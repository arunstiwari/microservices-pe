### 1. Create the payment domain entity
```java
@Table(name = "PAYMENTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private int id;
    private String transactionId;
    private String paymentStatus;
    private double amount;
}
```

### 2. Create the Payment repository
```java
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
```
### 3. Create the Payment service
```java
@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;

    public Payment doThePayment(Payment payment){
        String transactionId = UUID.randomUUID().toString();
        payment.setTransactionId(transactionId);
        return repository.save(payment);
    }
}

```
### 4. Create the Payment Controller
```java
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public Payment doThePayment(@RequestBody Payment payment){
        return paymentService.doThePayment(payment);
    }
}
```