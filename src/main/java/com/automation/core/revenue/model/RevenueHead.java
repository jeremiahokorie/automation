package com.automation.core.revenue.model;

import com.automation.core.mda.model.mdaModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "revenue_heads")
public class RevenueHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String revenueHeadCode;

    @Column(nullable = false)
    private BigDecimal amount;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "mda_revenue_head_mapping",
        joinColumns = @JoinColumn(name = "revenue_head_id"),
        inverseJoinColumns = @JoinColumn(name = "mda_id")
    )
    private List<mdaModel> mdas = new ArrayList<>();
}
