package by.mishota.graduation.controller.command;

import by.mishota.graduation.controller.command.impl.*;
import by.mishota.graduation.controller.command.impl.ViewNewsGetCommand;
import by.mishota.graduation.controller.command.impl.admin.*;

import static by.mishota.graduation.service.ServiceFactory.getInstance;

public enum CommandEnum {
    LOGIN_GET(new LoginGetCommand()),
    LOGIN_POST(new LoginPostCommand(getInstance().getUserService(), getInstance().getEntrantService(), getInstance().getFacultyService())),
    LOGOUT(new LogoutCommand()),
    SIGN_UP_GET(new SignUpGetCommand()),
    SIGN_UP_POST(new SignUpPostCommand(getInstance().getUserService())),
    MAIN_GET(new MainGetCommand(getInstance().getNewsService())),
    FORGOTTEN_PASSWORD_GET(new ForgottenPasswordGetCommand()),
    FORGOTTEN_PASSWORD_POST(new ForgottenPasswordPostCommand(getInstance().getUserService())),
    FORGOTTEN_PASSWORD_RESULT_GET(new ForgottenPasswordResultGetCommand()),
    ACTIVATION_FORGOTTEN_PASSWORD_RESULT_GET(new ActivationForgottenPasswordResultGetCommand()),
    ACTIVATION_FORGOTTEN_PASSWORD_GET(new ActivationForgottenPasswordGetCommand(getInstance().getUserService())),
    ACTIVATION_FORGOTTEN_PASSWORD_POST(new ActivationForgottenPasswordPostCommand(getInstance().getUserService())),
    ENTER_CERTIFICATES_GET(new EnterCertificatesGetCommand(getInstance().getSubjectService(), getInstance().getEntrantService())),
    ENTER_CERTIFICATES_POST(new EnterCertificatesPostCommand(getInstance().getSubjectService(), getInstance().getEntrantService())),
    ENTER_PRIORITY_FACULTIES_GET(new EnterPriorityFacultiesGetCommand(getInstance().getFacultyService())),
    ENTER_PRIORITY_FACULTIES_POST(new EnterPriorityFacultiesPostCommand(getInstance().getFacultyService(), getInstance().getEntrantService())),
    DELETE_PRIORITY_FACULTIES_GET(new DeletePriorityFacultiesGetCommand(getInstance().getFacultyService())),
    DELETE_PRIORITY_FACULTIES_POST(new DeletePriorityFacultiesPostCommand(getInstance().getEntrantService())),
    DELETE_CERTIFICATES_GET(new DeleteCertificatesGetCommand(getInstance().getSubjectService())),
    DELETE_CERTIFICATES_POST(new DeleteCertificatesPostCommand(getInstance().getEntrantService())),
    UPDATE_CERTIFICATES_GET(new UpdateCertificatesGetCommand(getInstance().getSubjectService())),
    UPDATE_CERTIFICATES_POST(new UpdateCertificatesPostCommand(getInstance().getEntrantService(), getInstance().getSubjectService())),
    CHANGING_LANGUAGE_POST(new ChangingLanguagePostCommand()),
    VIEW_CERTIFICATES_GET(new ViewCertificatesGetCommand(getInstance().getSubjectService())),
    VIEW_PRIORITY_FACULTIES_GET(new ViewPriorityFacultiesGetCommand(getInstance().getFacultyService(), getInstance().getEntrantService())),
    UPLOAD_PHOTO_GET(new UploadGetCommand()),
    VIEW_PHOTOS_GET(new ViewPhotosGetCommand(getInstance().getUserService())),
    DELETE_PHOTOS_GET(new DeletePhotosGetCommand(getInstance().getUserService())),
    DELETE_PHOTOS_POST(new DeletePhotosPostCommand(getInstance().getUserService())),
    ACTIVATION(new ActivationPostCommand(getInstance().getUserService())),
    UPDATE_PROFILE_GET(new UpdateProfileGetCommand()),
    UPDATE_PROFILE_POST(new UpdateProfilePostCommand(getInstance().getUserService())),
    VIEW_PROFILE_GET(new ViewProfileGetCommand()),
    VIEW_FACULTIES_GET(new ViewFacultiesGetCommand(getInstance().getFacultyService(), getInstance().getSubjectService())),
    VIEW_NEWS_GET(new ViewNewsGetCommand(getInstance().getNewsService(), getInstance().getUserService())),

    ADMIN_VIEW_ENTRANTS_GET(new ViewEntrantsGetCommand(getInstance().getSubjectService(), getInstance().getEntrantService())),
    ADMIN_VIEW_PROFILE_ENTRANT_GET(new ViewProfileEntrantGetCommand(getInstance().getSubjectService(), getInstance().getEntrantService(), getInstance().getFacultyService())),
    ADMIN_ADD_NEWS_GET(new AddNewsGetCommand()),
    ADMIN_ADD_NEWS_POST(new AddNewsPostCommand(getInstance().getNewsService())),
    ADMIN_VIEW_ALL_NEWS_GET(new ViewAllNewsGetCommand(getInstance().getNewsService())),
    ADMIN_VIEW_NEWS_GET(new ViewNewsGetCommand(getInstance().getNewsService(), getInstance().getUserService())),
    ADMIN_UPDATE_ALL_NEWS_GET(new UpdateAllNewsGetCommand(getInstance().getNewsService())),
    ADMIN_DELETE_ALL_NEWS_GET(new DeleteAllNewsGetCommand(getInstance().getNewsService())),
    ADMIN_DELETE_ALL_NEWS_POST(new DeleteAllNewsPostCommand(getInstance().getNewsService())),
    ADMIN_UPDATE_NEWS_GET(new UpdateNewsGetCommand(getInstance().getNewsService())),
    ADMIN_UPDATE_NEWS_POST(new UpdateNewsPostCommand(getInstance().getNewsService()));

    ActionCommand command;

    CommandEnum(ActionCommand loginCommand) {
        this.command = loginCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }

    public boolean equalsIgnoreCase(String nameCommand) {
        return this.toString().equalsIgnoreCase(nameCommand);
    }
}
