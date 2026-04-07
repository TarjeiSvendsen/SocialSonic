package tari.socialsonic.database.user.roles;


import jakarta.persistence.*;

@Embeddable
public class UserRoles {
    private boolean adminRole = false;
    private boolean settingsRole = true;
    private boolean streamRole = true;
    private boolean jukeboxRole = false;
    private boolean downloadRole = false;
    private boolean uploadRole = false;
    private boolean playlistRole = false;
    private boolean coverArtRole = false;
    private boolean commentRole = false;
    private boolean podcastRole = false;
    private boolean shareRole = false;
    private boolean videoConversionRole = false;

    public UserRoles(){
    }




    public boolean hasAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public boolean hasSettingsRole() {
        return settingsRole;
    }

    public void setSettingsRole(boolean settingsRole) {
        this.settingsRole = settingsRole;
    }

    public boolean hasStreamRole() {
        return streamRole;
    }

    public void setStreamRole(boolean streamRole) {
        this.streamRole = streamRole;
    }

    public boolean hasJukeboxRole() {
        return jukeboxRole;
    }

    public void setJukeboxRole(boolean jukeboxRole) {
        this.jukeboxRole = jukeboxRole;
    }

    public boolean hasDownloadRole() {
        return downloadRole;
    }

    public void setDownloadRole(boolean downloadRole) {
        this.downloadRole = downloadRole;
    }

    public boolean hasUploadRole() {
        return uploadRole;
    }

    public void setUploadRole(boolean uploadRole) {
        this.uploadRole = uploadRole;
    }

    public boolean hasPlaylistRole() {
        return playlistRole;
    }

    public void setPlaylistRole(boolean playlistRole) {
        this.playlistRole = playlistRole;
    }

    public boolean hasCoverArtRole() {
        return coverArtRole;
    }

    public void setCoverArtRole(boolean coverArtRole) {
        this.coverArtRole = coverArtRole;
    }

    public boolean hasCommentRole() {
        return commentRole;
    }

    public void setCommentRole(boolean commentRole) {
        this.commentRole = commentRole;
    }

    public boolean hasPodcastRole() {
        return podcastRole;
    }

    public void setPodcastRole(boolean podcastRole) {
        this.podcastRole = podcastRole;
    }

    public boolean hasShareRole() {
        return shareRole;
    }

    public void setShareRole(boolean shareRole) {
        this.shareRole = shareRole;
    }

    public boolean hasVideoConversionRole() {
        return videoConversionRole;
    }

    public void setVideoConversionRole(boolean videoConversionRole) {
        this.videoConversionRole = videoConversionRole;
    }
}
