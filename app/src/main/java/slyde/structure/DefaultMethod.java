package slyde.structure;

public class DefaultMethod {

    private String returnType;
    private Runnable dependancyMangment;
    private Runnable override;

    public DefaultMethod(String returnType) {
        this.returnType = returnType;
    }

    public DefaultMethod(String returnType, Runnable dependencies) {
        this(returnType);
        this.dependancyMangment = dependencies;
    }

    public DefaultMethod(String returnType, Runnable dependencies, Runnable override) {
        this(returnType, dependencies);
        this.override = override;
    }

    public Runnable getDependancyMangment() {
        return dependancyMangment;
    }

    public Runnable getOverride() {
        return override;
    }

    public String getReturnType() {
        return returnType;
    }

}
