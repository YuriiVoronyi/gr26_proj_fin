package de.aittr.gr26_proj_fin.security.sec_dto;

import java.util.Objects;

public class RefreshRequestDto {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshRequestDto that = (RefreshRequestDto) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RefreshRequestDto{");
        sb.append("refreshToken='").append(refreshToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
