package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.controllers.MasterController;
import com.example.beautySalon_contracts.form.create.MasterCreateFormModel;
import com.example.beautySalon_contracts.form.edit.MasterEditFormModel;
import com.example.beautySalon_contracts.viewModel.*;
import com.example.beautySalon_contracts.viewModel.edit.MasterEditViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.miit.beautySalon.services.AppointmentService;
import rut.miit.beautySalon.services.BeautySalonService;
import rut.miit.beautySalon.services.Impl.AuthService;
import rut.miit.beautySalon.services.MasterService;
import rut.miit.beautySalon.services.dtos.*;
import rut.miit.beautySalon.services.dtos.createDto.MasterCreateDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/masters")
public class MasterControllerImpl implements MasterController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final MasterService masterService;
    private final BeautySalonService beautySalonService;
    private final AppointmentService appointmentService;
    private final AuthService authService;

    public MasterControllerImpl (MasterService masterService, BeautySalonService beautySalonService, AppointmentService appointmentService, AuthService authService) {
        this.masterService = masterService;
        this.beautySalonService = beautySalonService;
        this.appointmentService = appointmentService;
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDto")
    public MasterCreateDto initForm() {
        return new MasterCreateDto();
    }

    @Override
    @GetMapping
    public String masterList (Principal principal, Model model){
        if (principal != null)
            LOG.log(Level.INFO, "Show list of masters for " + principal.getName());
        var masters = masterService.findAllByRole("MASTER");
        var masterViewModel = masters.stream()
                .map(m -> new MasterViewModel(m.getId(), m.getName(), m.getSurname(), m.getPatronymic(), m.getEmail(), "m.getLogin()"," m.getPassword()"))
                .toList();
        var viewModel = new MasterListViewModel(
                createBaseViewModel("Список мастеров"),
                masterViewModel
        );
        model.addAttribute("model", viewModel);
        return "master/master-list";
    }

    @Override
    @GetMapping("/register")
    public String register(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show register form of master for " + principal.getName());

        var login = new LoginViewModel(createBaseViewModel("Регистрация"));
        model.addAttribute("model", login);
        model.addAttribute("form", new MasterCreateFormModel("", "", "", "", "", "", ""));
        return "master/master-create";
    }

    @Override
    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("form") MasterCreateFormModel form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/masters/register";
        }

        BeautySalonDto beautySalonDto = beautySalonService.findAll().get(0);
        MasterCreateDto masterCreateDto = new MasterCreateDto(beautySalonDto.getId(), form.surname(), form.name(), form.patronymic(), form.email(), form.username(), form.password(), form.confirmPassword());
        this.authService.registerMaster(masterCreateDto);

        return "redirect:/masters";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(Principal principal, String id, Model model) {
        LOG.log(Level.INFO, "Show edit form of master for " + principal.getName());

        var master = masterService.findById(id);
        var viewModel = new MasterEditViewModel(createBaseViewModel("Редактирование мастера"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new MasterEditFormModel(master.getId(), master.getName(), master.getSurname(), master.getPatronymic(), master.getEmail()));
        return "master/master-edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable String id, @Valid @ModelAttribute("form") MasterEditFormModel form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new MasterEditViewModel(createBaseViewModel("Редактирование мастера"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "master/master-edit";
        }
        MasterDto oldMaster = masterService.findById(id);

        BeautySalonDto beautySalonDto = beautySalonService.findAll().get(0);
        MasterDto masterDto = new MasterDto(form.id(), beautySalonDto.getId(), form.name(), form.surname(), form.patronymic(), form.email(), oldMaster.getUsername(), oldMaster.getPassword());

        masterService.update(masterDto);
        return "redirect:/masters";
    }

    @Override
    @GetMapping("/rating")
    public String ratingMasters(Principal principal, Model model) {
        if (principal != null)
            LOG.log(Level.INFO, "Show rating list of masters for " + principal.getName());

        var rating = masterService.rating();
        List<RatingMastersViewModel> ratingMastersViewModels = new ArrayList<>();
        for (int i = 0 ; i < rating.size(); i++) {
            List<String> feedbacks = rating.get(i).getFeedbackDtoList().stream()
                    .map(f -> f.getFeedback()).toList();
            String master = rating.get(i).getMaster();
            ratingMastersViewModels.add(new RatingMastersViewModel(master, feedbacks));
        }

        var viewModel = new RatingMastersListViewModel(
                createBaseViewModel("Рейтинг мастеров"),
                ratingMastersViewModels
        );
        model.addAttribute("model", viewModel);
        return "master/master-rating";
    }

    @Override
    @GetMapping("/appointments")
    public String appointmentsMasters(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show list of appointments for " + principal.getName());

        String username = principal.getName();
        MasterDto masterDto = authService.getMaster(username);
        List<AppointmentDto> appointments = appointmentService.findAllByMaster(masterDto.getId());
        var appointmentViewModels = appointments.stream()
                .map(a -> new AppointmentViewModel(a.getId(), a.getMastersAttendanceAttendanceName(), a.getMastersAttendanceMasterSurname() + " " + a.getMastersAttendanceMasterName() + " " + a.getMastersAttendanceMasterPatronymic(), a.getClientName(), a.getDate()))
                .toList();
        var viewModel = new AppointmentListViewModel(
                createBaseViewModel("Мои записи"), appointmentViewModels);
        model.addAttribute("model", viewModel);
        return "appointment/appointment-list";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}

