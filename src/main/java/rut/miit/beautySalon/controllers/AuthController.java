package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.viewModel.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.miit.beautySalon.services.Impl.AuthService;
import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.ClientCreateDto;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDto")
    public ClientCreateDto initForm() {
        return new ClientCreateDto();
    }

    @GetMapping("/register")
    public String register(Model model) {
        var login = new LoginViewModel(createBaseViewModel("Регистрация"));
        model.addAttribute("model", login);
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid ClientCreateDto userRegistrationDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/users/register";
        }

        this.authService.registerClient(userRegistrationDto);

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        var login = new LoginViewModel(createBaseViewModel("Вход"));
        model.addAttribute("model", login);
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profileClient")
    public String profileClient (Principal principal, Model model) {
        LOG.log(Level.INFO, "Show profile for " + principal.getName());

        String username = principal.getName();
        ClientDto clientDto = authService.getClient(username);

        ClientProfileViewModel userProfileView = new ClientProfileViewModel(
                createBaseViewModel("Профиль"),
                clientDto.getName(),
                clientDto.getEmail(),
                username,
                clientDto.getPassword()
        );

        model.addAttribute("model", userProfileView);

        return "profileClient";
    }

    @GetMapping("/profileMaster")
    public String profileMaster (Principal principal, Model model) {
        LOG.log(Level.INFO, "Show profile for " + principal.getName());

        String username = principal.getName();

        MasterDto masterDto = authService.getMaster(username);

        MasterViewModel userProfileView = new MasterViewModel(
                masterDto.getId(),
                masterDto.getName(),
                masterDto.getSurname(),
                masterDto.getPatronymic(),
                masterDto.getEmail(),
                username,
                masterDto.getPassword()
        );

        MasterListViewModel masterListViewModel = new MasterListViewModel(createBaseViewModel("Профиль"), List.of(userProfileView));

        model.addAttribute("model", masterListViewModel);

        return "profileMaster";
    }

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}

