package gateway.client;

import gateway.dto.RequestUserDto;
import gateway.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        RequestUserDto requestUserDto = new RequestUserDto("testName", "test@email.com");
        UserDto userDto = new UserDto(1L, "testName", "test@email.com");
        ResponseEntity<UserDto> responseEntity = ResponseEntity.ok(userDto);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        ResponseEntity<UserDto> result = userClient.add(requestUserDto);

        assertThat(result.getBody()).isEqualTo(userDto);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class));
    }

    @Test
    void testUpdateUser() {
        long userId = 1L;
        RequestUserDto requestUserDto = new RequestUserDto("testName", "test@email.com");
        UserDto userDto = new UserDto(userId, "testName", "test@email.com");
        ResponseEntity<UserDto> responseEntity = ResponseEntity.ok(userDto);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        ResponseEntity<UserDto> result = userClient.updateUser(userId, requestUserDto);

        assertThat(result.getBody()).isEqualTo(userDto);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(UserDto.class));
    }

    @Test
    void testDeleteUser() {
        long userId = 1L;

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> result = userClient.deleteUser(userId);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class));
    }

    @Test
    void testGetUserById() {
        long userId = 1L;
        UserDto userDto = new UserDto(userId, "testName", "test@email.com");
        ResponseEntity<UserDto> responseEntity = ResponseEntity.ok(userDto);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenReturn(responseEntity);

        ResponseEntity<UserDto> result = userClient.getUserById(userId);

        assertThat(result.getBody()).isEqualTo(userDto);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class));
    }

    @Test
    void testAdd_WhenServerReturnsError() {
        RequestUserDto requestUserDto = new RequestUserDto("testName", "test@email.com");
        String errorMessage = "Email already exists";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, errorMessage));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> userClient.add(requestUserDto));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).contains(errorMessage);
    }

    @Test
    void testUpdateUser_WhenUserNotFound() {
        long userId = 999L;
        RequestUserDto requestUserDto = new RequestUserDto("testName", "test@email.com");
        String errorMessage = "User not found with id: " + userId;

        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, errorMessage));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> userClient.updateUser(userId, requestUserDto));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).contains(errorMessage);
    }

    @Test
    void testDeleteUser_WhenUserNotFound() {
        long userId = 999L;
        String errorMessage = "User not found with id: " + userId;

        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, errorMessage));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> userClient.deleteUser(userId));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).contains(errorMessage);
    }

    @Test
    void testGetUserById_WhenUserNotFound() {
        long userId = 999L;
        String errorMessage = "User not found with id: " + userId;

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, errorMessage));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> userClient.getUserById(userId));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).contains(errorMessage);
    }

    @Test
    void testAdd_WhenServerUnavailable() {
        RequestUserDto requestUserDto = new RequestUserDto("testName", "test@email.com");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new RestClientException("Connection refused"));

        RestClientException exception = assertThrows(RestClientException.class,
                () -> userClient.add(requestUserDto));

        assertThat(exception.getMessage()).contains("Connection refused");
    }

    @Test
    void testUpdateUser_WhenValidationFailed() {
        long userId = 1L;
        RequestUserDto requestUserDto = new RequestUserDto("", "invalid-email");
        String errorMessage = "Validation failed";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(UserDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, errorMessage));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> userClient.updateUser(userId, requestUserDto));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).contains(errorMessage);
    }
}