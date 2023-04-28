package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Controller для {@link AccountDetailsEntity}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/details", produces = "application/json")
@Validated
@Tag(name = "Банковский счет", description = "API для получения информации о банковском счете")
public class AccountDetailsController {

    private final AccountDetailsService service;

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Найти банковский счет по ID",
            description = "Возвращает информацию о банковском счете",
            tags = {"Банковский счет"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная операция"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Передан некорректный id",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Банковский счет с таким id не найден",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> read(
             @Parameter(description = "Id запрошенного банковского счета")
             @NotNull @PositiveOrZero @PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Найти банковские счета по Id",
            description = "Возвращает информацию о банковских счетах",
            tags = {"Банковский счет"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная операция"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Передан один или несколько некорректных id",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Банковский счет с переданными id не найден",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<AccountDetailsDto>> readAll(
            @Parameter(description = "Id запрошенных банковских счетов") @RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAllById(ids), HttpStatus.OK);
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Добавление информации о банковском счете",
            description = "В запросе может быть передан только один JSON",
            tags = {"Банковский счет"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная операция"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Передан некорректный JSON",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping
    public ResponseEntity<AccountDetailsDto> create(
            @Parameter(description = "Тело JSON банковского счета на создание")
            @Valid @RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.create(accountDetails), HttpStatus.OK);
    }

    /**
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Обновить банковский счет по Id",
            description = "Обновление информации о банковском счете по Id",
            tags = {"Банковский счет"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная операция"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Передан некорректный id или JSON",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Банковский счет с переданными id не найден",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> update(
            @Parameter(description = "Id банковского счета для обновления")
            @NotNull @PositiveOrZero @PathVariable Long id,
            @Parameter(description = "Тело JSON банковского счета на обновление")
            @Valid @RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.update(id, accountDetails), HttpStatus.OK);
    }
}
