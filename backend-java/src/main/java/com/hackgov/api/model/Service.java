package com.hackgov.api.model;

/**
 * Um serviço público (fictício) oferecido pelo portal, incluindo os indicadores de
 * desempenho/transparência mostrados na seção de performance do front-end.
 */
public class Service {

    private String slug;
    private String icon;
    private String tag;
    private String name;
    private String timeEstimate;
    private String description;
    private String[] keywords;
    private String[] requirements;
    private boolean needsBiometrics;
    private String simpleExplanation;

    /** Nota média de satisfação (0.0 a 5.0). */
    private float rating;
    /** Tempo médio de conclusão, em dias. */
    private int avgDays;
    /** Total de reclamações registradas. */
    private int complaints;
    /** Percentual de solicitações resolvidas dentro do prazo previsto. */
    private int resolvedPct;
    /** Principal problema relatado pelos usuários. */
    private String mainIssue;

    public Service() {
    }

    public Service(String slug, String icon, String tag, String name, String timeEstimate,
                   String description, String[] keywords, String[] requirements,
                   boolean needsBiometrics, String simpleExplanation, float rating,
                   int avgDays, int complaints, int resolvedPct, String mainIssue) {
        this.slug = slug;
        this.icon = icon;
        this.tag = tag;
        this.name = name;
        this.timeEstimate = timeEstimate;
        this.description = description;
        this.keywords = keywords;
        this.requirements = requirements;
        this.needsBiometrics = needsBiometrics;
        this.simpleExplanation = simpleExplanation;
        this.rating = rating;
        this.avgDays = avgDays;
        this.complaints = complaints;
        this.resolvedPct = resolvedPct;
        this.mainIssue = mainIssue;
    }

    public String getSlug() {
        return slug;
    }

    public String getIcon() {
        return icon;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getTimeEstimate() {
        return timeEstimate;
    }

    public String getDescription() {
        return description;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public boolean isNeedsBiometrics() {
        return needsBiometrics;
    }

    public String getSimpleExplanation() {
        return simpleExplanation;
    }

    public float getRating() {
        return rating;
    }

    public int getAvgDays() {
        return avgDays;
    }

    public int getComplaints() {
        return complaints;
    }

    public int getResolvedPct() {
        return resolvedPct;
    }

    public String getMainIssue() {
        return mainIssue;
    }
}
