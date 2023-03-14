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
    private final MockMvc mock;

    @MockBean
    private BranchService service;

    @BeforeAll
    static void init() {
        branchSupplier = new BranchSupplier();

        branch = branchSupplier.getDto(ONE, SPACE, TWO, SPACE, TIME, TIME);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(branch).when(service).save(any());

        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());
        final String startOfWork = LocalTimeToString(branch.getStartOfWork());
        final String endOfWork = LocalTimeToString(branch.getEndOfWork());

        mock.perform(post("/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isOk(),
                jsonPath("$.address", is(branch.getAddress())),
                jsonPath("$.phoneNumber", is(phoneNumber)),
                jsonPath("$.city", is(branch.getCity())),
                jsonPath("$.startOfWork", is(startOfWork)),
                jsonPath("$.endOfWork", is(endOfWork))
        );
    }

    @Test
    @DisplayName("Сохранение  негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        mock.perform(post("/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readTest() throws Exception {
        doReturn(branch).when(service).read(any());

        final int id = getIntFromLong(branch.getId());
        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());
        final String startOfWork = LocalTimeToString(branch.getStartOfWork());
        final String endOfWork = LocalTimeToString(branch.getEndOfWork());

        mock.perform(get("/branch/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.address", is(branch.getAddress())),
                        jsonPath("$.phoneNumber", is(phoneNumber)),
                        jsonPath("$.city", is(branch.getCity())),
                        jsonPath("$.startOfWork", is(startOfWork)),
                        jsonPath("$.endOfWork", is(endOfWork))
                );
    }

    @Test
    @DisplayName("чтение, негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Отделения банка нет")).when(service).read(any());

        mock.perform(get("/branch/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Отделения банка нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAll() throws Exception {

        final List<BranchDto> branches = returnBranches();

        doReturn(branches).when(service).readAll(any());

        final BranchDto zeroBranch = branches.get(0);
        final BranchDto oneBranch = branches.get(1);

        final int zeroId = getIntFromLong(zeroBranch.getId());
        final int zeroPhoneNumber = getIntFromLong(zeroBranch.getPhoneNumber());
        final String zeroStartOfWork = LocalTimeToString(zeroBranch.getStartOfWork());
        final String zeroEndOfWork = LocalTimeToString(zeroBranch.getEndOfWork());

        final int oneId = getIntFromLong(oneBranch.getId());
        final int onePhoneNumber = getIntFromLong(oneBranch.getPhoneNumber());
        final String oneStartOfWork = LocalTimeToString(oneBranch.getStartOfWork());
        final String oneEndOfWork = LocalTimeToString(oneBranch.getEndOfWork());

        mock.perform(get("/branch?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(branches.size())),
                        jsonPath("$.[0].id", is(zeroId)),
                        jsonPath("$.[0].address", is(zeroBranch.getAddress())),
                        jsonPath("$.[0].phoneNumber", is(zeroPhoneNumber)),
                        jsonPath("$.[0].city", is(zeroBranch.getCity())),
                        jsonPath("$.[0].startOfWork", is(zeroStartOfWork)),
                        jsonPath("$.[0].endOfWork", is(zeroEndOfWork)),
                        jsonPath("$.[1].id", is(oneId)),
                        jsonPath("$.[1].address", is(oneBranch.getAddress())),
                        jsonPath("$.[1].phoneNumber", is(onePhoneNumber)),
                        jsonPath("$.[1].city", is(oneBranch.getCity())),
                        jsonPath("$.[1].startOfWork", is(oneStartOfWork)),
                        jsonPath("$.[1].endOfWork", is(oneEndOfWork))
                );
    }

    private List<BranchDto> returnBranches() {
        return List.of(
                branchSupplier.getDto(ONE, SPACE, TWO, SPACE, TIME, TIME),
                branchSupplier.getDto(ONE, SPACE, TWO, SPACE, TIME, TIME)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/branch?id=1")).andExpectAll(status().isNotFound(),
                content().string("Ошибка в параметрах")
        );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(branch).when(service).update(anyLong(), any());

        final int id = getIntFromLong(branch.getId());
        final int phoneNumber = getIntFromLong(branch.getPhoneNumber());
        final String startOfWork = LocalTimeToString(branch.getStartOfWork());
        final String endOfWork = LocalTimeToString(branch.getEndOfWork());

        mock.perform(put("/branch/{id}", ONE).
                contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.address", is(branch.getAddress())),
                jsonPath("$.phoneNumber", is(phoneNumber)),
                jsonPath("$.city", is(branch.getCity())),
                jsonPath("$.startOfWork", is(startOfWork)),
                jsonPath("$.endOfWork", is(endOfWork))
        );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        mock.perform(put("/branch/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branch))
        ).andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
