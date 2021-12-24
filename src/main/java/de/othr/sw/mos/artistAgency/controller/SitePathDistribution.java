package de.othr.sw.mos.artistAgency.controller;

public interface SitePathDistribution {
    // default
    String indexSite = "default/index";

    // error
    String errorSite = "default/error";

    // artist
    String artistDetailsSite = "artist/artistDetails";
    String artistListSite = "artist/artistList";

    // event
    String eventListSite = "event/eventList";
    String bookNewEventSite = "event/eventList";

    // finance
    String financeList = "finance/financeList";

    // user
    String loginSite = "user/login";
    String registerUserSite = "user/registerUser";
    String myProfileSite = "user/myProfile";
    String editMyProfileSite = "user/editMyProfile";
}
