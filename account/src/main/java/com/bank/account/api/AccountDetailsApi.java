package com.bank.account.api;

import com.bank.account.dto.AccountDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "AccountDetails", description = "An API for getting account details")
public interface AccountDetailsApi {
    @Operation(
            summary = "Find AccountDetails by ID",
            description = "Returns a single AccountDetails",
            tags = {"AccountDetails"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id provided",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "AccountDetails with provided id is not found",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    ResponseEntity<AccountDetailsDto> read(
            @Parameter(description = "Id of AccountDetails to return") @PathVariable Long id
    );

    @Operation(
            summary = "Find multiple AccountDetails by ids",
            description = """
                    Multiple ids can be provided with comma separated strings or standard &.
                    Use /details?id=1,2,3 or /details?id=1&id=2&id=3 for testing
                    """,
            tags = {"AccountDetails"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id or ids provided",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No AccountDetails found with provided ids",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    ResponseEntity<List<AccountDetailsDto>> readAll(
            @Parameter(description = "Ids to find") @RequestParam("id") List<Long> ids
    );

    @Operation(
            summary = "Add a new AccountDetails to the Profile",
            description = "Only single JSON can be provided per request",
            tags = {"AccountDetails"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid json provided",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    ResponseEntity<AccountDetailsDto> create(
            @Parameter(description = "AccountDetails JSON body to create") @RequestBody AccountDetailsDto accountDetails
    );

    @Operation(
            summary = "Update an existing AccountDetails",
            description = "Update an existing AccountDetails by id",
            tags = {"AccountDetails"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id or json body provided",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "AccountDetails with provided id is not found",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            )
    })
    ResponseEntity<AccountDetailsDto> update(
            @Parameter(description = "Id of AccountDetails to update") @PathVariable Long id,
            @Parameter(description = "AccountDetails JSON body to update") @RequestBody AccountDetailsDto accountDetails
    );
}
