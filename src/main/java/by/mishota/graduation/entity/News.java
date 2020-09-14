package by.mishota.graduation.entity;

import java.time.LocalDateTime;

public class News {
    private int id;
    private int userCreatorId;
    private String nameRu;
    private String nameEn;
    private String briefDescriptionRu;
    private String briefDescriptionEn;
    private String englishVariable;
    private String russianVariable;
    private LocalDateTime creationDate;

    public News(int id, int userCreatorId, String nameRu, String nameEn, String briefDescriptionRu, String briefDescriptionEn, String englishVariable, String russianVariable, LocalDateTime creationDate) {
        this.id = id;
        this.userCreatorId = userCreatorId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.briefDescriptionRu = briefDescriptionRu;
        this.briefDescriptionEn = briefDescriptionEn;
        this.englishVariable = englishVariable;
        this.russianVariable = russianVariable;
        this.creationDate = creationDate;
    }

    public News(int userCreatorId, String nameRu, String nameEn, String briefDescriptionRu, String briefDescriptionEn, String englishVariable, String russianVariable, LocalDateTime creationDate) {
        this.userCreatorId = userCreatorId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.briefDescriptionRu = briefDescriptionRu;
        this.briefDescriptionEn = briefDescriptionEn;
        this.englishVariable = englishVariable;
        this.russianVariable = russianVariable;
        this.creationDate = creationDate;
    }

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(int userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getBriefDescriptionRu() {
        return briefDescriptionRu;
    }

    public void setBriefDescriptionRu(String briefDescriptionRu) {
        this.briefDescriptionRu = briefDescriptionRu;
    }

    public String getBriefDescriptionEn() {
        return briefDescriptionEn;
    }

    public void setBriefDescriptionEn(String briefDescriptionEn) {
        this.briefDescriptionEn = briefDescriptionEn;
    }

    public String getEnglishVariable() {
        return englishVariable;
    }

    public void setEnglishVariable(String englishVariable) {
        this.englishVariable = englishVariable;
    }

    public String getRussianVariable() {
        return russianVariable;
    }

    public void setRussianVariable(String russianVariable) {
        this.russianVariable = russianVariable;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (userCreatorId != news.userCreatorId) return false;
        if (nameRu != null ? !nameRu.equals(news.nameRu) : news.nameRu != null) return false;
        if (nameEn != null ? !nameEn.equals(news.nameEn) : news.nameEn != null) return false;
        if (briefDescriptionRu != null ? !briefDescriptionRu.equals(news.briefDescriptionRu) : news.briefDescriptionRu != null)
            return false;
        if (briefDescriptionEn != null ? !briefDescriptionEn.equals(news.briefDescriptionEn) : news.briefDescriptionEn != null)
            return false;
        if (englishVariable != null ? !englishVariable.equals(news.englishVariable) : news.englishVariable != null)
            return false;
        if (russianVariable != null ? !russianVariable.equals(news.russianVariable) : news.russianVariable != null)
            return false;
        return creationDate != null ? creationDate.equals(news.creationDate) : news.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userCreatorId;
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (briefDescriptionRu != null ? briefDescriptionRu.hashCode() : 0);
        result = 31 * result + (briefDescriptionEn != null ? briefDescriptionEn.hashCode() : 0);
        result = 31 * result + (englishVariable != null ? englishVariable.hashCode() : 0);
        result = 31 * result + (russianVariable != null ? russianVariable.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("News( ")
                .append(id).append(", id of user creator")
                .append(userCreatorId).append(", nameRu: ")
                .append(nameRu).append(", nameEn: ")
                .append(nameEn).append(".\n Brief descriptionRu:")
                .append(briefDescriptionRu).append(".\n Brief descriptionEn:")
                .append(briefDescriptionEn).append("\nEnglish variable:\n")
                .append(englishVariable).append("\nRussian variable:\nDate creation")
                .append(creationDate).append(");\n").toString();
    }
}
