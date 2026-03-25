package org.bank.serviceaccount;

import org.bank.serviceaccount.exception.GlobalExceptionHandler;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.entity.Account;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.repository.AccountRepository;
import org.bank.serviceaccount.security.jwt.JwtService;
import org.bank.serviceaccount.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void testServiceRepoCreateAcc() {
        var dto = new AccountCredentialsDto();
        dto.setEmail("1@1.com");
        dto.setPassword("112");
        var role = AccountRole.CLIENT;
        var enabled = false;
        var expectedEntity = new Account();

        expectedEntity.setEmail(dto.getEmail());
        expectedEntity.setPassword(dto.getPassword());
        expectedEntity.setRole(role);
        expectedEntity.setEnabled(enabled);

        when(modelMapper.map(dto, Account.class)).thenReturn(expectedEntity);
        ResponseEntity<?> result = accountService.register(dto, role, enabled);
        assertThat(result.getStatusCode().is2xxSuccessful());
        verify(accountRepository).save(expectedEntity);
    }

    @Test
    public void testExistsEmail() {
        var dto = new AccountCredentialsDto();
        dto.setEmail("1@1.com");
        dto.setPassword("112");
        var role = AccountRole.CLIENT;
        var enabled = false;
        when(accountRepository.existsByEmail(dto.getEmail())).thenReturn(true);
        Throwable exception = catchThrowable(() -> accountService.register(dto, role, enabled));
        assertThat(exception).isInstanceOf(GlobalExceptionHandler.ConflictException.class)
                .hasMessage("Email already exists");
        verify(accountRepository).existsByEmail(dto.getEmail());
        verifyNoInteractions(modelMapper, passwordEncoder);
    }
}
