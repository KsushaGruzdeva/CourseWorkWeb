package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.controllers.ClientController;
import com.example.beautySalon_contracts.form.create.*;
import com.example.beautySalon_contracts.form.edit.ClientEditFormModel;
import com.example.beautySalon_contracts.viewModel.*;
import com.example.beautySalon_contracts.viewModel.create.*;
import com.example.beautySalon_contracts.viewModel.edit.ClientEditViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.beautySalon.services.*;
import rut.miit.beautySalon.services.Impl.AuthService;
import rut.miit.beautySalon.services.dtos.*;
import rut.miit.beautySalon.services.dtos.createDto.AppointmentCreateDto;
import rut.miit.beautySalon.services.dtos.createDto.FeedbackCreateDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientControllerImpl implements ClientController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final ClientService clientService;
    private final AppointmentService appointmentService;
    private final AttendanceService attendanceService;
    private final MastersAttendanceService mastersAttendanceService;
    private final FeedbackService feedbackService;
    private final AuthService authService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    @Autowired
    public ClientControllerImpl (ClientService clientService, AppointmentService appointmentService, AttendanceService attendanceService, MastersAttendanceService mastersAttendanceService, FeedbackService feedbackService, AuthService authService) {
        this.clientService = clientService;
        this.appointmentService = appointmentService;
        this.attendanceService = attendanceService;
        this.mastersAttendanceService = mastersAttendanceService;
        this.feedbackService = feedbackService;
        this.authService = authService;
    }

    @Override
    @GetMapping("/edit")
    public String editForm(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show edit form of client for " + principal.getName());
        String username = principal.getName();
        ClientDto client = authService.getClient(username);
        var viewModel = new ClientEditViewModel(createBaseViewModel("Редактирование профиля"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new ClientEditFormModel(client.getId(), client.getName(), client.getEmail(), client.getUsername()));
        return "client/client-edit";
    }

    @Override
    @PostMapping("/edit")
    public String edit(Principal principal, @Valid @ModelAttribute("form") ClientEditFormModel form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new ClientEditViewModel(createBaseViewModel("Редактирование профиля"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "client/client-edit";
        }
        String username = principal.getName();
        ClientDto oldClient = authService.getClient(username);
        ClientDto clientDto = new ClientDto(form.id(), form.name(), form.login(), oldClient.getPassword(), form.email());

        clientService.update(clientDto);
        return "redirect:/client";
    }

    @Override
    @GetMapping("/createAppointmentAttendance")
    public String createAppointmentAttendanceForm (Principal principal, Model model) {
        LOG.log(Level.INFO, "Show create form of appointment attendance for " + principal.getName());
        List<AttendanceDto> attendanceDtos = attendanceService.findAll();
        List<String> attendances = attendanceDtos.stream().map(AttendanceDto::getName).toList();
        var viewModel = new AppointmentCreateAttendanceViewModel(createBaseViewModel("Запись на услугу"), attendances);
        model.addAttribute("model", viewModel);
        model.addAttribute("formAttendance", new AppointmentCreateAttendanceFormModel(""));
        return "appointment/appointment-attendance-create";
    }

    @Override
    @PostMapping("/createAppointmentAttendance")
        public String createAppointmentAttendance (Principal principal, AppointmentCreateAttendanceFormModel formAttendance, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<AttendanceDto> attendanceDtos = attendanceService.findAll();
            List<String> attendances = attendanceDtos.stream().map(AttendanceDto::getName).toList();
            var viewModel = new AppointmentCreateAttendanceViewModel(createBaseViewModel("Запись на услугу"), attendances);
            model.addAttribute("model", viewModel);
            model.addAttribute("formAttendance", formAttendance);
            return "appointment/appointment-attendance-create";
        }
        AttendanceDto attendanceDto = attendanceService.findByName(formAttendance.attendance());
        return "redirect:/client/" + attendanceDto.getId() + "/createAppointmentTime";
    }

    @Override
    @GetMapping("/{attendanceId}/createAppointmentTime")
    public String createAppointmentTimeForm (Principal principal, String attendanceId, Model model) {
        LOG.log(Level.INFO, "Show create form of appointment time for " + principal.getName());
        var viewModel = new AppointmentCreateTimeViewModel(createBaseViewModel("Запись на услугу"), attendanceId);
        model.addAttribute("model", viewModel);
        model.addAttribute("formTime", new AppointmentCreateTimeFormModel(LocalDateTime.now()));
        return "appointment/appointment-time-create";
    }

    @Override
    @PostMapping("/{attendanceId}/createAppointmentTime")
    public String createAppointmentTime (Principal principal, String attendanceId, AppointmentCreateTimeFormModel formTime, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            var viewModel = new AppointmentCreateTimeViewModel(createBaseViewModel("Запись на услугу"), attendanceId);
//            model.addAttribute("model", viewModel);
//            model.addAttribute("formTime", formTime);
//            return "appointment/appointment-time-create";
//        }

        LocalDateTime date = formTime.date();
        return "redirect:/client/" + attendanceId + "/" + formatter.format(date) + "/createAppointmentMaster";
    }

    @Override
    @GetMapping("/{attendanceId}/{time}/createAppointmentMaster")
    public String createAppointmentMasterForm(Principal principal, String attendanceId, String time, Model model) {
        LOG.log(Level.INFO, "Show create form of appointment master for " + principal.getName());

        LocalDateTime date = LocalDateTime.from(formatter.parse(time));
        List<MasterDto> masterDtos = appointmentService.findFreeMasters(date, attendanceId);
        if (masterDtos.isEmpty()) {
            return "redirect:/client/" + attendanceId + "/createAppointmentTime";
        }
        List<String> masters = masterDtos.stream().map(m -> m.getSurname() + " " + m.getName() + " " + m.getPatronymic()).toList();
        var viewModel = new AppointmentCreateViewModel(createBaseViewModel("Запись на услугу"), attendanceId, time, masters);
        model.addAttribute("model", viewModel);
        model.addAttribute("formMaster", new AppointmentCreateFormModel(""));
        return "appointment/appointment-master-create";
    }

    @Override
    @PostMapping("/{attendanceId}/{time}/createAppointmentMaster")
    public String createAppointmentMaster (Principal principal, String attendanceId, String time, AppointmentCreateFormModel formMaster, BindingResult bindingResult, Model model) {
        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);
        if (bindingResult.hasErrors()) {
            AttendanceDto attendanceDto = attendanceService.findById(attendanceId);
            List<MasterDto> masterDtos = mastersAttendanceService.findMasterByAttendance(attendanceDto.getId());
            List<String> masters = masterDtos.stream().map(m -> m.getSurname() + " " + m.getName() + " " + m.getPatronymic()).toList();
            var viewModel = new AppointmentCreateViewModel(createBaseViewModel("Запись на услугу"), attendanceId, time, masters);
            model.addAttribute("model", viewModel);
            model.addAttribute("formMaster", formMaster);
            return "appointment/appointment-master-create";
        }
        LocalDateTime date = LocalDateTime.from(formatter.parse(time));

        AttendanceDto attendanceDto = attendanceService.findById(attendanceId);
        String[] fio = formMaster.master().split(" ");
        System.out.println(fio[0] + " " + fio[1] + " " + fio[2]);
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(attendanceDto.getName(), fio[0], fio[1], fio[2], clientDto.getName(), date);
        appointmentService.create(appointmentCreateDto);
        return "redirect:/client/appointments";
    }

    @Override
    @GetMapping("/appointments")
    public String listAppointments (Principal principal, Model model) {
        LOG.log(Level.INFO, "Show list of appointments for " + principal.getName());

        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);
        List<AppointmentDto> appointments = appointmentService.findAllByClient(clientDto.getId());
        var appointmentViewModels = appointments.stream()
                .map(a -> new AppointmentViewModel(a.getId(), a.getMastersAttendanceAttendanceName(), a.getMastersAttendanceMasterSurname() + " " + a.getMastersAttendanceMasterName() + " " + a.getMastersAttendanceMasterPatronymic(), a.getClientName(), a.getDate()))
                .toList();
        var viewModel = new AppointmentListViewModel(
                createBaseViewModel("Мои записи"), appointmentViewModels);
        model.addAttribute("model", viewModel);
        return "appointment/appointment-list";
    }

    @Override
    @GetMapping("/feedbacks")
    public String listFeedbacks (Principal principal, Model model) {
        LOG.log(Level.INFO, "Show list of feedbacks for " + principal.getName());

        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);
        List<FeedbackDto> feedbacks = feedbackService.findAllByClient(clientDto.getId());
        var feedbackViewModels = feedbacks.stream()
                .map(f -> new FeedbackViewModel(f.getId(), f.getMasterName(), clientDto.getName(), f.getFeedback()))
                .toList();
        var viewModel = new FeedbackListViewModel(
                createBaseViewModel("Мои отзывы"), feedbackViewModels);
        model.addAttribute("model", viewModel);
        return "client/feedback-list";
    }

    @Override
    @GetMapping("/createFeedback")
    public String createFeedbackForm(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show create form of feedback for " + principal.getName());

        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);
        List<MasterDto> masterDtos = appointmentService.findMastersByClient(clientDto.getId());
        List<String> masters = masterDtos.stream().map(m -> m.getSurname() + " " + m.getName() + " " + m.getPatronymic()).toList();
        var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Создание отзыва"), masters);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new FeedbackCreateFormModel("", clientDto.getId(), ""));
        return "client/feedback-create";
    }

    @Override
    @PostMapping("/createFeedback")
    public String createFeedback(Principal principal, FeedbackCreateFormModel form, BindingResult bindingResult, Model model){
        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);
        if (bindingResult.hasErrors()) {
            System.out.println(clientDto.getName());
            List<MasterDto> masterDtos = appointmentService.findMastersByClient(clientDto.getId());
            List<String> masters = masterDtos.stream().map(m -> m.getSurname() + " " + m.getName() + " " + m.getPatronymic()).toList();
            var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Создание отзыва"), masters);
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "client/feedback-create";
        }
        String[] fio = form.master().split(" ");
        FeedbackCreateDto feedbackDto = new FeedbackCreateDto(clientDto.getName(), fio[0], fio[1], fio[2], form.feedback());
        feedbackService.create(feedbackDto);
        return "redirect:/client/feedbacks";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}