package model;

import java.util.Date;

public class Disciplina {
    private Integer   numeroDisciplina;
    private String    nomeDisciplina;
    private Date      dataInicio;
    private Date      dataEncerramento;
    private Professor professorResponsavel;
    private Curso     curso;

    public Disciplina() {}

    public Disciplina(Integer numeroDisciplina, String nomeDisciplina,
                      Date dataInicio, Date dataEncerramento,
                      Professor professorResponsavel, Curso curso) {
        this.numeroDisciplina    = numeroDisciplina;
        this.nomeDisciplina      = nomeDisciplina;
        this.dataInicio          = dataInicio;
        this.dataEncerramento    = dataEncerramento;
        this.professorResponsavel = professorResponsavel;
        this.curso               = curso;
    }

    public Disciplina(Integer numeroDisciplina, String nomeDisciplina,
                      Date dataInicio, Date dataEncerramento, Professor professorResponsavel) {
        this(numeroDisciplina, nomeDisciplina, dataInicio, dataEncerramento, professorResponsavel, null);
    }

    public Integer   getNumeroDisciplina()               { return numeroDisciplina; }
    public void      setNumeroDisciplina(Integer v)      { this.numeroDisciplina = v; }
    public String    getNomeDisciplina()                 { return nomeDisciplina; }
    public void      setNomeDisciplina(String v)         { this.nomeDisciplina = v; }
    public Date      getDataInicio()                     { return dataInicio; }
    public void      setDataInicio(Date v)               { this.dataInicio = v; }
    public Date      getDataEncerramento()               { return dataEncerramento; }
    public void      setDataEncerramento(Date v)         { this.dataEncerramento = v; }
    public Professor getProfessorResponsavel()           { return professorResponsavel; }
    public void      setProfessorResponsavel(Professor v){ this.professorResponsavel = v; }
    public Curso     getCurso()                          { return curso; }
    public void      setCurso(Curso v)                   { this.curso = v; }
}
