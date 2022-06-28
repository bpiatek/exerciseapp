package pl.bpiatek.exerciseapp.github;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import pl.bpiatek.exerciseapp.github.api.app.AppResponse;

/**
 * Created by Bartosz Piatek on 28/06/2022
 */
@Tag(name = "Github controller")
interface OpenApiGithubController {
  @Operation(summary = "Get information about github user by passing it's login")
  @Parameter(name = "login", description = "login of github user you want to get information about")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved information about user"),
      @ApiResponse(responseCode = "404", description = "User does not exist")
  })
  ResponseEntity<AppResponse> getUserInfoByLogin(@PathVariable String login);
}
