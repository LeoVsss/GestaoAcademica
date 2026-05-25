package model;

public class Curso {
    private Integer codCurso;
    private String nomeCurso;
    private String descCurso;

    public Curso() {}

    public Curso(Integer codCurso, String nomeCurso, String descCurso) {
        this.codCurso  = codCurso;
        this.nomeCurso = nomeCurso;
        this.descCurso = descCurso;
    }

    public Integer getCodCurso()            { return codCurso; }
    public void    setCodCurso(Integer v)   { this.codCurso = v; }
    public String  getNomeCurso()           { return nomeCurso; }
    public void    setNomeCurso(String v)   { this.nomeCurso = v; }
    public String  getDescCurso()           { return descCurso; }
    public void    setDescCurso(String v)   { this.descCurso = v; }

    @Override public String toString() { return nomeCurso != null ? nomeCurso : ""; }
}
