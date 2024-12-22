package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.controllers.AppointmentController;
import com.example.beautySalon_contracts.viewModel.AppointmentListViewModel;
import com.example.beautySalon_contracts.viewModel.AppointmentViewModel;
import com.example.beautySalon_contracts.viewModel.BaseViewModel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.miit.beautySalon.services.AppointmentService;
import rut.miit.beautySalon.services.dtos.AppointmentDto;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentControllerImpl implements AppointmentController {
    private final AppointmentService appointmentService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public AppointmentControllerImpl(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @Override
    public String ListAppointments(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show list of appointments for " + principal.getName());
        List<AppointmentDto> appointments = appointmentService.findAll();
        var appointmentViewModels = appointments.stream()
                .map(a -> new AppointmentViewModel(a.getId(), a.getMastersAttendanceAttendanceName(), a.getMastersAttendanceMasterSurname() + " " + a.getMastersAttendanceMasterName() + " " + a.getMastersAttendanceMasterPatronymic(), a.getClientName(), a.getDate()))
                .toList();
        var viewModel = new AppointmentListViewModel(
                createBaseViewModel("Все записи"), appointmentViewModels);
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
