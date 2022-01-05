package de.othr.sw.mos.artistAgency.controller.util;

public interface SitePathDistribution {
    // default
    String indexSite = "default/index";

    // error
    String errorSite = "default/error";

    // artist
    String artistDetailsSite = "artist/artistDetails";
    String artistListSite = "artist/artistList";
    String myProfileSite = "artist/myProfile";
    String editMyProfileSite = "artist/editMyProfile";

    // event
    String eventListSite = "event/eventList";
    String bookNewEventSite = "event/bookNewEvent";

    // finance
    String financeList = "finance/financeList";

    // user
    String loginSite = "user/login";
    String loginDefaultUserSite = "user/loginDefaultUserForDevelopment";
    String registerUserSite = "user/registerUser";
}
