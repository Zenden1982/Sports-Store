// package com.zenden.sports_store.Tests;

// import java.util.Collections;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.when;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.http.MediaType;
// import static
// org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
// import org.springframework.test.web.servlet.MockMvc;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.zenden.sports_store.Classes.AuthRequest;
// import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
// import com.zenden.sports_store.Classes.DTO.UserReadDTO;
// import com.zenden.sports_store.Filters.User.UserFilter;
// import com.zenden.sports_store.Security.JwtTokenUtils;
// import com.zenden.sports_store.Services.UserService;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserControllerTest {

// @Autowired
// private WebApplicationContext context;

// private MockMvc mockMvc;

// @MockBean
// private UserService userService;

// @MockBean
// private JwtTokenUtils jwtTokenUtils;

// @Autowired
// private ObjectMapper objectMapper;

// private UserCreateUpdateDTO userCreateUpdateDTO;
// private UserReadDTO userReadDTO;
// private AuthRequest authRequest;
// private UserFilter userFilter;

// @BeforeEach
// public void setUp() {
// mockMvc = MockMvcBuilders
// .webAppContextSetup(context)
// .apply(springSecurity())
// .build();

// userCreateUpdateDTO = new UserCreateUpdateDTO();
// userCreateUpdateDTO.setUsername("testuser");
// userCreateUpdateDTO.setPassword("password");

// userReadDTO = new UserReadDTO();
// userReadDTO.setId(1L);
// userReadDTO.setUsername("testuser");

// authRequest = new AuthRequest();
// authRequest.setUsername("testuser");
// authRequest.setPassword("password");

// userFilter = new UserFilter();
// // initialize userFilter as needed
// }

// @Test
// public void testCreateUser() throws Exception {
// when(userService.create(any(UserCreateUpdateDTO.class))).thenReturn(userReadDTO);

// mockMvc.perform(post("/api/users")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userCreateUpdateDTO)))
// .andExpect(status().isCreated())
// .andExpect(content().json(objectMapper.writeValueAsString(userReadDTO)));
// }

// @Test
// public void testConfirmUser() throws Exception {
// // Assuming confirm redirects to another page, we cannot test the redirect
// itself easily,
// // but we can test that the service method was called.
// mockMvc.perform(get("/api/users/confirm")
// .param("token", "someToken"))
// .andExpect(status().is3xxRedirection());
// }

// @Test
// public void testLoginUser() throws Exception {
// String token = "mockToken";
// when(userService.generateToken(any(AuthRequest.class))).thenReturn(token);

// mockMvc.perform(post("/api/users/login")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(authRequest)))
// .andExpect(status().isOk())
// .andExpect(content().string(token));
// }

// @Test
// public void testUserData() throws Exception {
// String token = "mockToken";
// String username = "testuser";
// when(jwtTokenUtils.getUsernameFromToken(token)).thenReturn(username);

// mockMvc.perform(post("/api/users/username")
// .param("token", token))
// .andExpect(status().isOk())
// .andExpect(content().string(username));
// }

// @Test
// public void testRole() throws Exception {
// String token = "mockToken";
// when(jwtTokenUtils.getRolesFromToken(token)).thenReturn(List.of("ROLE_USER",
// "ROLE_ADMIN"));

// mockMvc.perform(get("/api/users/role")
// .param("token", token))
// .andExpect(status().isOk())
// .andExpect(content().json("[\"ROLE_USER\", \"ROLE_ADMIN\"]"));
// }

// @Test
// public void testReadUser() throws Exception {
// when(userService.read(1L)).thenReturn(userReadDTO);

// mockMvc.perform(get("/api/users/1")
// .contentType(MediaType.APPLICATION_JSON))
// .andExpect(status().isOk())
// .andExpect(content().json(objectMapper.writeValueAsString(userReadDTO)));
// }

// @Test
// public void testReadAllUsers() throws Exception {
// Page<UserReadDTO> page = new
// PageImpl<>(Collections.singletonList(userReadDTO));
// when(userService.readAll(eq(0), eq(10), eq("id"), any(UserFilter.class)))
// .thenReturn(page);

// mockMvc.perform(post("/api/users/all")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userFilter))
// .param("page", "0")
// .param("size", "10")
// .param("sort", "id"))
// .andExpect(status().isOk())
// .andExpect(content().json(objectMapper.writeValueAsString(page)));
// }

// @Test
// public void testUpdateUser() throws Exception {
// when(userService.update(eq(1L),
// any(UserCreateUpdateDTO.class))).thenReturn(userReadDTO);

// mockMvc.perform(put("/api/users/1")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userCreateUpdateDTO)))
// .andExpect(status().isOk())
// .andExpect(content().json(objectMapper.writeValueAsString(userReadDTO)));
// }

// @Test
// public void testDeleteUser() throws Exception {
// mockMvc.perform(delete("/api/users/1"))
// .andExpect(status().isOk());
// }
// }
