package org.doif.projectv.business.client.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "tel", length = 11, nullable = false)
    private String tel;

    @Column(name = "biz_reg_no", length = 10, nullable = false)
    private String bizRegNo;

    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @Column(name = "basic_addr", nullable = false)
    private String basicAddr;

    @Column(name = "detail_addr", nullable = false)
    private String detailAddr;

    public Client(String name, String description, String tel, String bizRegNo, String zipCode, String basicAddr, String detailAddr) {
        this.name = name;
        this.description = description;
        this.tel = tel;
        this.bizRegNo = bizRegNo;
        this.zipCode = zipCode;
        this.basicAddr = basicAddr;
        this.detailAddr = detailAddr;
    }

    public void changeClient(String name, String description, String tel, String bizRegNo, String zipCode, String basicAddr, String detailAddr) {
        this.name = name;
        this.description = description;
        this.tel = tel;
        this.bizRegNo = bizRegNo;
        this.zipCode = zipCode;
        this.basicAddr = basicAddr;
        this.detailAddr = detailAddr;
    }
}
