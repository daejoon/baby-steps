package com.ddoong2.webapp.settings;

import com.ddoong2.webapp.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class Profile {

    @Size(max = 35)
    private String bio;
    @Size(max = 50)
    private String url;
    @Size(max = 50)
    private String occupation;
    @Size(max = 50)
    private String location;

    private String profileImage;

    public Profile(final Account account) {
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
    }
}
