package org.ems.models;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bibin on 5/10/16.
 */

@Entity
@Table(name = "child_node")
public class StudentNode implements Serializable {

    private static final long serialVersionUID = 7450539180039911995L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "band_code")
    private String bandCode;

    @Column(name = "age_range")
    private String ageRange;

    @ManyToOne(targetEntity = ParentNode.class)
    @JoinColumn(name = "parent_student_id", referencedColumnName = "id")
    private ParentNode parentNode;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "studentNode", targetEntity = InOutInformer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InOutInformer> inOutInformerList = new ArrayList<>();

    private transient Boolean checkIn;

    private transient Boolean checkOut;

    public StudentNode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return new StringBuilder(firstName).append(" ").append(lastName).toString();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBandCode() {
        return bandCode;
    }

    public void setBandCode(String bandCode) {
        this.bandCode = bandCode;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public ParentNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(ParentNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<InOutInformer> getInOutInformerList() {
        return inOutInformerList;
    }

    public void setInOutInformerList(List<InOutInformer> inOutInformerList) {
        this.inOutInformerList = inOutInformerList;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Boolean getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Boolean checkOut) {
        this.checkOut = checkOut;
    }

    public Boolean getHasInEntryOnDate() {
        String currentDateInStringFormat = getDateAsString();
        Boolean inFlag = false;
        if (!inOutInformerList.isEmpty()) {
            for (InOutInformer inOutInformer : inOutInformerList) {
                if (inOutInformer.getDate().equals(currentDateInStringFormat)) {
                    if (inOutInformer.getInTime() != null && !inOutInformer.getInTime().toString().isEmpty()) {
                        inFlag = true;
                    }
                }
            }
        }
        return inFlag;
    }

    public Boolean getHasOutEntryOnDate() {
        String currentDateInStringFormat = getDateAsString();
        Boolean outFlag = false;
        if (!inOutInformerList.isEmpty()) {
            for (InOutInformer inOutInformer : inOutInformerList) {
                if (inOutInformer.getDate().equals(currentDateInStringFormat)) {
                    if (inOutInformer.getOutTime() != null && !inOutInformer.getOutTime().toString().isEmpty()) {
                        outFlag = true;
                    }
                }
            }
        }
        return outFlag;
    }

    public String getInTimes(String date) {
        Map<String, String> lookUpMap = new HashMap<>();
        lookUpMap.put("dayOne", "Oct-29");
        lookUpMap.put("dayTwo", "Oct-30");
        lookUpMap.put("dayThree", "Oct-31");
        lookUpMap.put("dayFour", "Nov-1");
        if (!inOutInformerList.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy, HH:MM");
            StringBuilder stringBuilder = new StringBuilder();
            for (InOutInformer inOutInformer : inOutInformerList) {
                String DateFromUI = lookUpMap.get(date) != null ? lookUpMap.get(date) : org.apache.commons.lang.StringUtils.EMPTY;
                Boolean flagToEmptyCheckInForCurrentDate;
                if (DateFromUI.isEmpty()) {
                    flagToEmptyCheckInForCurrentDate = true;
                } else {
                    flagToEmptyCheckInForCurrentDate = inOutInformer.getDate().equals(lookUpMap.get(date));
                }
                if (null != inOutInformer.getInTime() && flagToEmptyCheckInForCurrentDate) {
                    stringBuilder.append(dateFormatter.format(inOutInformer.getInTime())).append("\n");
                }
            }
            return stringBuilder.toString();
        }
        return org.apache.commons.lang.StringUtils.EMPTY;
    }

    public String getOutTimes(String date) {
        Map<String, String> lookUpMap = new HashMap<>();
        lookUpMap.put("dayOne", "Oct-29");
        lookUpMap.put("dayTwo", "Oct-30");
        lookUpMap.put("dayThree", "Oct-31");
        lookUpMap.put("dayFour", "Nov-1");
        if (!inOutInformerList.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy, HH:MM");
            StringBuilder stringBuilder = new StringBuilder();
            for (InOutInformer inOutInformer : inOutInformerList) {
                String DateFromUI = lookUpMap.get(date) != null ? lookUpMap.get(date) : org.apache.commons.lang.StringUtils.EMPTY;
                Boolean flagToEmptyCheckInForCurrentDate;
                if (DateFromUI.isEmpty()) {
                    flagToEmptyCheckInForCurrentDate = true;
                } else {
                    flagToEmptyCheckInForCurrentDate = inOutInformer.getDate().equals(lookUpMap.get(date));
                }
                if (null != inOutInformer.getOutTime() && flagToEmptyCheckInForCurrentDate) {
                    stringBuilder.append(dateFormatter.format(inOutInformer.getOutTime())).append("\n");
                }
            }
            return stringBuilder.toString();
        }
        return org.apache.commons.lang.StringUtils.EMPTY;
    }

    public void addInOutInformer(InOutInformer inOutInformer) {
        this.inOutInformerList.add(inOutInformer);
    }

    private String getDateAsString() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM-dd");
        String currentDateInStringFormat = dateFormatter.format(currentDate);
        if (currentDateInStringFormat.equalsIgnoreCase("Nov-01")) {
            currentDateInStringFormat = currentDateInStringFormat.replace("0", "");
        }
        return currentDateInStringFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentNode that = (StudentNode) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parentNode != null ? !parentNode.equals(that.parentNode) : that.parentNode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentNode != null ? parentNode.hashCode() : 0);
        return result;
    }
}
