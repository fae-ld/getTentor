package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.Favorite;

public class FavoriteDTO {
    private Integer menteeId;
    private Integer tentorId;

    public FavoriteDTO(Favorite favorite) {
        this.menteeId = favorite.getMentee().getId();
        this.tentorId = favorite.getTentor().getId();
    }

    public Integer getMenteeId() {
        return menteeId;
    }

    public Integer getTentorId() {
        return tentorId;
    }
}
