package rut.miit.beautySalon.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.beautySalon_contracts.form.create.AttendanceCreateFormModel;
import com.example.beautySalon_contracts.form.edit.AttendanceEditFormModel;
import com.example.beautySalon_contracts.viewModel.AttendanceListViewModel;
import com.example.beautySalon_contracts.viewModel.AttendanceViewModel;
import com.example.beautySalon_contracts.viewModel.BaseViewModel;
import com.example.beautySalon_contracts.viewModel.create.AttendanceCreateViewModel;
import com.example.beautySalon_contracts.viewModel.edit.AttendanceEditViewModel;
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
import com.example.beautySalon_contracts.controllers.AttendanceController;
import rut.miit.beautySalon.services.AttendanceService;
import rut.miit.beautySalon.services.MasterService;
import rut.miit.beautySalon.services.MastersAttendanceService;
import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.AttendanceCreateDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterAttendanceCreateDto;

@Controller
@RequestMapping("/attendances")
public class AttendanceControllerImpl implements AttendanceController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final AttendanceService attendanceService;
    private final MastersAttendanceService mastersAttendanceService;
    private final MasterService masterService;

    public AttendanceControllerImpl(AttendanceService attendanceService, MastersAttendanceService mastersAttendanceService, MasterService masterService) {
        this.attendanceService = attendanceService;
        this.masterService = masterService;
        this.mastersAttendanceService = mastersAttendanceService;
    }

    @GetMapping
    public String ListAttendances(Principal principal, Model model) {
        if (principal != null)
            LOG.log(Level.INFO, "Show list of attendances for " + principal.getName());
        var attendances = attendanceService.findAll();
        List<List<String>> masters = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            List<MasterDto> masterDto = mastersAttendanceService.findMasterByAttendance(attendances.get(i).getId());
            List<String> master = masterDto.stream().map(m -> m.getSurname() + " " + m.getName() + " " + m.getPatronymic()).toList();
            masters.add(master);
        }
        List<AttendanceViewModel> attendanceViewModel = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            attendanceViewModel.add(new AttendanceViewModel(attendances.get(i).getId(), attendances.get(i).getName(), attendances.get(i).getPrice(), masters.get(i)));
        }
        var viewModel = new AttendanceListViewModel(
                createBaseViewModel("Список услуг"),
                attendanceViewModel
        );
        model.addAttribute("model", viewModel);
        return "attendance/attendance-list";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show create form of attendance for " + principal.getName());
        List<String> masters = new ArrayList<String>();
        List<MasterDto> masterDto = masterService.findAllByRole("MASTER");
        for (int i=0; i<masterDto.size(); i++){
            String master = masterDto.get(i).getSurname() + " " + masterDto.get(i).getName() + " " + masterDto.get(i).getPatronymic();
            masters.add(master);
        }
        var viewModel = new AttendanceCreateViewModel(createBaseViewModel("Создание услуги"), masters);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AttendanceCreateFormModel("", 0, masters));
        return "attendance/attendance-create";
    }

    @Override
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") AttendanceCreateFormModel form,
            BindingResult bindingResult,
            Model model){
        List<String> masters = new ArrayList<String>();
        List<MasterDto> masterDto = masterService.findAll();
        for (int i=0; i<masterDto.size(); i++){
            String master = masterDto.get(i).getSurname() + " " + masterDto.get(i).getName() + " " + masterDto.get(i).getPatronymic();
            masters.add(master);
        }
        if (bindingResult.hasErrors()) {
            var viewModel = new AttendanceCreateViewModel(createBaseViewModel("Создание услуги"), masters);
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "attendance/attendance-create";
        }
        AttendanceCreateDto attendanceCreateDto = new AttendanceCreateDto(form.name(), form.price());
        var attendanceDto = attendanceService.create(attendanceCreateDto);

        List<String> masterFIO = form.masters();
        for (int i=0; i<masterFIO.size(); i++) {
            String [] masterFio = masterFIO.get(i).split(" ");
            String name = masterFio[1];
            String surname = masterFio[0];
            String patronymic = masterFio[2];
            MasterDto master = masterService.findByFIO(name, surname, patronymic);
            MasterAttendanceCreateDto mastersAttendanceCreateDto = new MasterAttendanceCreateDto (attendanceDto.getId(), master.getId());
            mastersAttendanceService.create(mastersAttendanceCreateDto);
        }

        return "redirect:/attendances";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(Principal principal, String id, Model model) {
        LOG.log(Level.INFO, "Show edit form of attendance for " + principal.getName());
        var attendance = attendanceService.findById(id);
        List<String> masters = new ArrayList<String>();
        List<MasterDto> masterDto = masterService.findAll();
        for (int i=0; i<masterDto.size(); i++){
            masters.add(masterDto.get(i).getId());
        }
        var viewModel = new AttendanceEditViewModel(createBaseViewModel("Редактирование услуги"), masters);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AttendanceEditFormModel(attendance.getId(), attendance.getName(), attendance.getPrice()));
        return "attendance/attendance-edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable String id, @Valid @ModelAttribute("form") AttendanceEditFormModel form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> masters = new ArrayList<String>();
            List<MasterDto> masterDto = masterService.findAll();
            for (int i=0; i<masterDto.size(); i++){
                masters.add(masterDto.get(i).getId());
            }
            var viewModel = new AttendanceEditViewModel(createBaseViewModel("Редактирование услуги"), masters);
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "attendance/attendance-edit";
        }
        AttendanceDto attendanceDto = new AttendanceDto(form.id(), form.name(), form.price());

        attendanceService.update(attendanceDto);
        return "redirect:/attendances";
    }
    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}
