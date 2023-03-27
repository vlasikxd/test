package com.bank.publicinfo.controller;

import com.bank.common.exception.ValidationException;
import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import com.bank.publicinfo.supplier.BranchSupplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BranchControllerTest extends ParentTest {

    private static BranchDto branch;
    private static BranchSupplier branchSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private BranchService service;

    @BeforeAll
    static void init() {
        branchSupplier = new BranchSupplier();

        branch = branchSupplier.getDto(ONE, SPACE, TWO, SPACE);
    }

    @Test
    @DisplayName("cохранение, позитивный сценарий")
    void savePositiveTest() throws Exception {
        doReturn(branch).when(service).save(any());

        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());

        mockMvc.perform(post("/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isOk(),
                jsonPath("$.city", is(branch.getCity())),
                jsonPath("$.phoneNumber", is(phoneNumber)),
                jsonPath("$.address", is(branch.getAddress())),
                jsonPath("$.endOfWork", is(toStringLocalTime(branch.getEndOfWork()))),
                jsonPath("$.startOfWork", is(toStringLocalTime(branch.getStartOfWork())))
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() throws Exception {
        String errorMessage = "Неверные данные";

        doThrow(new ValidationException(errorMessage)).when(service).save(any());

        mockMvc.perform(post("/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(errorMessage)
        );
    }

    @Test
    @DisplayName("сохранение, передан pdf, негативный сценарий")
    void saveWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                post("/branch")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().is5xxServerError());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(branch).when(service).read(any());

        final int id = getIntFromLong(branch.getId());
        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());

        mockMvc.perform(get("/branch/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.city", is(branch.getCity())),
                        jsonPath("$.phoneNumber", is(phoneNumber)),
                        jsonPath("$.address", is(branch.getAddress())),
                        jsonPath("$.endOfWork", is(toStringLocalTime(branch.getEndOfWork()))),
                        jsonPath("$.startOfWork", is(toStringLocalTime(branch.getStartOfWork())))
                );
    }

    @Test
    @DisplayName("чтение, негативный сценарий")
    void readNegativeTest() throws Exception {
        String errorMessage = "Отделения банка нет";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).read(any());

        mockMvc.perform(get("/branch/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readWrongIdNegativeTest() throws Exception {
        mockMvc.perform(get("/branch/test"))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {

        final List<BranchDto> branches = returnBranches();

        doReturn(branches).when(service).readAll(any());

        final BranchDto oneBranch = branches.get(1);
        final BranchDto zeroBranch = branches.get(0);

        final int zeroId = getIntFromLong(zeroBranch.getId());
        final int zeroPhoneNumber = getIntFromLong(zeroBranch.getPhoneNumber());

        final int oneId = getIntFromLong(oneBranch.getId());
        final int onePhoneNumber = getIntFromLong(oneBranch.getPhoneNumber());

        mockMvc.perform(get("/branch?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(branches.size())),
                        jsonPath("$.[0].id", is(zeroId)),
                        jsonPath("$.[0].city", is(zeroBranch.getCity())),
                        jsonPath("$.[0].phoneNumber", is(zeroPhoneNumber)),
                        jsonPath("$.[0].address", is(zeroBranch.getAddress())),
                        jsonPath("$.[0].endOfWork", is(toStringLocalTime(zeroBranch.getEndOfWork()))),
                        jsonPath("$.[0].startOfWork", is(toStringLocalTime(zeroBranch.getStartOfWork()))),
                        jsonPath("$.[1].id", is(oneId)),
                        jsonPath("$.[1].city", is(oneBranch.getCity())),
                        jsonPath("$.[1].phoneNumber", is(onePhoneNumber)),
                        jsonPath("$.[1].address", is(oneBranch.getAddress())),
                        jsonPath("$.[1].endOfWork", is(toStringLocalTime(oneBranch.getEndOfWork()))),
                        jsonPath("$.[1].startOfWork", is(toStringLocalTime(oneBranch.getStartOfWork())))
                );
    }

    private List<BranchDto> returnBranches() {
        return List.of(
                branchSupplier.getDto(ONE, SPACE, TWO, SPACE),
                branchSupplier.getDto(ONE, SPACE, TWO, SPACE)
        );
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNoIdNegativeTest() throws Exception {
        final String errorMessage = "Ошибка в параметрах";
        doThrow(new EntityNotFoundException(errorMessage)).when(service).readAll(any());

        mockMvc.perform(get("/branch?id=1")).andExpectAll(status().isNotFound(),
                content().string(errorMessage));
    }

    @Test
    @DisplayName("чтение по нескольким id, один из id некорректен негативный сценарий")
    void readAllWrongIdNegativeTest() throws Exception {
        mockMvc.perform(get("/branch?id=1&id=test"))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(branch).when(service).update(anyLong(), any());

        final int id = getIntFromLong(branch.getId());
        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());

        mockMvc.perform(put("/branch/{id}", ONE).
                contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.city", is(branch.getCity())),
                jsonPath("$.phoneNumber", is(phoneNumber)),
                jsonPath("$.address", is(branch.getAddress())),
                jsonPath("$.endOfWork", is(toStringLocalTime(branch.getEndOfWork()))),
                jsonPath("$.startOfWork", is(toStringLocalTime(branch.getStartOfWork())))
        );
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNoIdNegativeTest() throws Exception {
        String errorMessage = "Обновление невозможно";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/branch/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isNotFound(),
                content().string(errorMessage));
    }

    @Test
    @DisplayName("обновление по некорректному id, негативный сценарий")
    void updateWrongIdNegativeTest() throws Exception {
        mockMvc.perform(put("/branch/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().is4xxClientError());
    }
}
