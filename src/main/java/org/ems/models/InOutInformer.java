package org.rms.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tijo on 26/10/16.
 */

@Entity
@Table(name = "in_out_info")
public class InOutInformer implements Serializable {

    private static final long serialVersionUID = 4772244041038030835L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;

    private Date inTime;

    private Date outTime;

    private Long doneInBy;

    private Long doneOutBy;

    @ManyToOne(targetEntity = StudentNode.class)
    @JoinColumn(name = "student_inoutinfo_id", referencedColumnName = "id")
    private StudentNode studentNode;

    public InOutInformer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Long getDoneInBy() {
        return doneInBy;
    }

    public void setDoneInBy(Long doneBy) {
        this.doneInBy = doneBy;
    }

    public Long getDoneOutBy() {
        return doneOutBy;
    }

    public void setDoneOutBy(Long doneOutBy) {
        this.doneOutBy = doneOutBy;
    }

    public StudentNode getStudentNode() {
        return studentNode;
    }

    public void setStudentNode(StudentNode studentNode) {
        this.studentNode = studentNode;
    }
}
