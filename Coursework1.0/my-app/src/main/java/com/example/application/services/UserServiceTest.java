package com.example.application.services;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test get() method")
    void testGet() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.get(userId);

        assertTrue(result.isPresent(), "Expected user to be present");
        assertEquals(userId, result.get().getId(), "Expected user ID to match");
        verify(repository, times(1)).findById(userId);

        System.out.println("Test get() method passed.");
    }

    @Test
    @DisplayName("Test get() method with non-existent user ID")
    void testGetWithNonExistentUserId() {
        Long nonExistentUserId = 999L;
        when(repository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        Optional<User> result = userService.get(nonExistentUserId);

        assertFalse(result.isPresent(), "Expected user to not be present");
        verify(repository, times(1)).findById(nonExistentUserId);

        System.out.println("Test get() method with non-existent user ID passed.");
    }

    @Test
    @DisplayName("Test update() method")
    void testUpdate() {
        User user = new User();
        when(repository.save(user)).thenReturn(user);

        User result = userService.update(user);

        assertNotNull(result, "Expected updated user to not be null");
        verify(repository, times(1)).save(user);

        System.out.println("Test update() method passed.");
    }

    @Test
    @DisplayName("Test update() method with null user")
    void testUpdateWithNullUser() {
        assertThrows(NullPointerException.class, () -> userService.update(null),
                "Expected NullPointerException when updating null user");

        verify(repository, never()).save(any(User.class));

        System.out.println("Test update() method with null user passed.");
    }

    @Test
    @DisplayName("Test delete() method")
    void testDelete() {
        Long userId = 1L;

        userService.delete(userId);

        verify(repository, times(1)).deleteById(userId);

        System.out.println("Test delete() method passed.");
    }

    @Test
    @DisplayName("Test list() method with Pageable")
    void testListWithPageable() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        Page<User> page = new PageImpl<>(users);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.list(Pageable.unpaged());

        assertNotNull(result, "Expected user page to not be null");
        assertEquals(2, result.getTotalElements(), "Expected 2 users in the page");
        verify(repository, times(1)).findAll(any(Pageable.class));

        System.out.println("Test list() method with Pageable passed.");
    }

    @Test
    @DisplayName("Test list() method with Pageable and Specification")
    void testListWithPageableAndSpecification() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        Page<User> page = new PageImpl<>(users);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.list(Pageable.unpaged(), mock(Specification.class));

        assertNotNull(result, "Expected user page to not be null");
        assertEquals(2, result.getTotalElements(), "Expected 2 users in the page");
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));

        System.out.println("Test list() method with Pageable and Specification passed.");
    }

    @Test
    @DisplayName("Test count() method")
    void testCount() {
        when(repository.count()).thenReturn(10L);

        int result = userService.count();

        assertEquals(10, result, "Expected user count to be 10");
        verify(repository, times(1)).count();

        System.out.println("Test count() method passed.");
    }
    @Test
    @DisplayName("Test get() method with invalid user ID")
    void testGetWithInvalidUserId() {
        Long invalidUserId = -1L;
        when(repository.findById(invalidUserId)).thenThrow(new IllegalArgumentException("Invalid user ID"));

        assertThrows(IllegalArgumentException.class, () -> userService.get(invalidUserId),
                "Expected IllegalArgumentException when getting user with invalid ID");

        verify(repository, times(1)).findById(invalidUserId);

        System.out.println("Test get() method with invalid user ID passed.");
    }

    @Test
    @DisplayName("Test update() method with user having null ID")
    void testUpdateWithUserHavingNullId() {
        User user = new User();
        user.setId(null);

        assertThrows(IllegalArgumentException.class, () -> userService.update(user),
                "Expected IllegalArgumentException when updating user with null ID");

        verify(repository, never()).save(any(User.class));

        System.out.println("Test update() method with user having null ID passed.");
    }

    @Test
    @DisplayName("Test delete() method with invalid user ID")
    void testDeleteWithInvalidUserId() {
        Long invalidUserId = -1L;

        assertThrows(IllegalArgumentException.class, () -> userService.delete(invalidUserId),
                "Expected IllegalArgumentException when deleting user with invalid ID");

        verify(repository, never()).deleteById(any(Long.class));

        System.out.println("Test delete() method with invalid user ID passed.");
    }

    @Test
    @DisplayName("Test list() method with null Pageable")
    void testListWithNullPageable() {
        assertThrows(NullPointerException.class, () -> userService.list((Pageable) null),
                "Expected NullPointerException when listing users with null Pageable");

        verify(repository, never()).findAll(any(Pageable.class));

        System.out.println("Test list() method with null Pageable passed.");
    }

    @Test
    @DisplayName("Test list() method with null Specification")
    void testListWithNullSpecification() {
        assertThrows(NullPointerException.class, () -> userService.list(Pageable.unpaged(), null),
                "Expected NullPointerException when listing users with null Specification");

        verify(repository, never()).findAll(any(Specification.class), any(Pageable.class));

        System.out.println("Test list() method with null Specification passed.");
    }
}