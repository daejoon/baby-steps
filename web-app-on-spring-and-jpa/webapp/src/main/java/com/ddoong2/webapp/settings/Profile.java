package com.ddoong2.webapp.settings;

import com.ddoong2.webapp.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profile {

    private String bio;
    private String url;
    private String occupation;
    private String location;

    public Profile(final Account account) {
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
    }
}
