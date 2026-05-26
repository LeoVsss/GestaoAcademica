package model;

import java.util.Date;

public class Professor {
    private Integer codProfessor;
    private String  nomeProfessor;
    private Date    dataNascProfessor;
    private Curso   curso;

    public Professor() {}

    public Professor(Curso curso, Integer codProfessor, String nomeProfessor, Date dataNascProfessor) {
        this.curso             = curso;
        this.codProfessor      = codProfessor;
        this.nomeProfessor     = nomeProfessor;
        this.dataNascProfessor = dataNascProfessor;
    }

    public Integer getCodProfessor()               { return codProfessor; }
    public void    setCodProfessor(Integer v)      { this.codProfessor = v; }
    public String  getNomeProfessor()              { return nomeProfessor; }
    public void    setNomeProfessor(String v)      { this.nomeProfessor = v; }
    public Date    getDataNascProfessor()           { return dataNascProfessor; }
    public void    setDataNascProfessor(Date v)     { this.dataNascProfessor = v; }
    public Curso   getCurso()                      { return curso; }
    public void    setCurso(Curso v)               { this.curso = v; }

    @Override public String toString() {
        return (codProfessor != null ? codProfessor : "") + " - " + (nomeProfessor != null ? nomeProfessor : "");
    }
}
