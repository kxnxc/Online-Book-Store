package com.example.onlinebookstore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();
    @Column(nullable = false)
    private boolean isDeleted = false;
}
