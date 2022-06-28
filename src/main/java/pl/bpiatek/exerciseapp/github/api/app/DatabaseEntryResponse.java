package pl.bpiatek.exerciseapp.github.api.app;

import lombok.Value;

/**
 * Created by Bartosz Piatek on 28/06/2022
 */
@Value
public class DatabaseEntryResponse {
   String login;
   Integer requestCount;
}
