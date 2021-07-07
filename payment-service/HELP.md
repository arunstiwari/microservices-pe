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

---
## Creating Association of payment with Order Id
### 1. Add the attribute `orderId` to the `Payment` domain entity
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
    private String orderId;
}
```
### 2. Also let us refactor the code to not get the `Payment status` from the user but internally manage it
```java
@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;

    public Payment payForOrder(Payment payment){
        String paymentStatus = paymentProcessing();
        payment.setPaymentStatus(paymentStatus);
        String transactionId = UUID.randomUUID().toString();
        payment.setTransactionId(transactionId);
        return repository.save(payment);
    }

    //In real world this is going to be status of call made to third party payment gateway
    private String paymentProcessing() {
        return new Random().nextBoolean()? "success": "failure";
    }
}

```
### 3. Next is we go to the OrderService and make a call to `Payment service` at the time of `booking order`


---
# Building Docker Image
### 1. Create a Dockerfile to build the layered image
```dockerfile
FROM adoptopenjdk:16-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-service.jar
RUN java -Djarmode=layertools -jar payment-service.jar extract

FROM adoptopenjdk:16-jre-hotspot
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

+ Add the following section in the `pom.xml` as shown below
```xml
<build>
        <finalName>payment-service</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <layers>
                        <enabled>true</enabled>
                    </layers>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>build-image</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
+ Build a docker image using the following command
```shell
docker build -t arunstiwari/payment-service:1.0 .

[+] Building 29.2s (13/13) FINISHED                                                                                                                      
 => [internal] load build definition from Dockerfile                                                                                                0.4s
 => => transferring dockerfile: 476B                                                                                                                0.0s
 => [internal] load .dockerignore                                                                                                                   0.5s
 => => transferring context: 2B                                                                                                                     0.0s
 => [internal] load metadata for docker.io/library/adoptopenjdk:16-jre-hotspot                                                                      6.0s
 => [auth] library/adoptopenjdk:pull token for registry-1.docker.io                                                                                 0.0s
 => [internal] load build context                                                                                                                   0.4s
 => => transferring context: 91B                                                                                                                    0.0s
 => [stage-1 1/5] FROM docker.io/library/adoptopenjdk:16-jre-hotspot@sha256:a15dabe64773a129d9c5ddfbd82e6e2d9cad4f22114ddcdecf350f84ea61aec7       14.1s
 => => resolve docker.io/library/adoptopenjdk:16-jre-hotspot@sha256:a15dabe64773a129d9c5ddfbd82e6e2d9cad4f22114ddcdecf350f84ea61aec7                0.0s
 => => sha256:a15dabe64773a129d9c5ddfbd82e6e2d9cad4f22114ddcdecf350f84ea61aec7 1.70kB / 1.70kB                                                      0.0s
 => => sha256:d998af291fcd2547cb0ef066288306519f8d80e14a5cebb4f876fdfb44353005 1.37kB / 1.37kB                                                      0.0s
 => => sha256:1e58bee8050bac674a19a79225e41c7cd2aad4afcefc839afd6df8b163d7a994 6.55kB / 6.55kB                                                      0.0s
 => => sha256:80bc30679ac1fd798f3241208c14accd6a364cb8a6224d1127dfb1577d10554f 27.14MB / 27.14MB                                                    0.0s
 => => sha256:9bf18fab4cfbf479fa9f8409ad47e2702c63241304c2cdd4c33f2a1633c5f85e 850B / 850B                                                          0.0s
 => => sha256:5979309c983a2adeff352538937475cf961d49c34194fa2aab142effe19ed9c1 189B / 189B                                                          0.0s
 => => sha256:b6482171366cbde14bd4de2fe7eade385c2d307e224a008056f39e553b9c93d7 15.90MB / 15.90MB                                                    0.0s
 => => sha256:848551405f1cae7421c62aba4958610bdf6a5c9867863a402b5f3308a66d6672 48.42MB / 48.42MB                                                   11.9s
 => => extracting sha256:848551405f1cae7421c62aba4958610bdf6a5c9867863a402b5f3308a66d6672                                                           0.6s
 => [builder 2/3] COPY target/*.jar payment-service.jar                                                                                             0.9s
 => [builder 3/3] RUN java -Djarmode=layertools -jar payment-service.jar extract                                                                    1.6s
 => [stage-1 2/5] COPY --from=builder dependencies/ ./                                                                                              0.9s
 => [stage-1 3/5] COPY --from=builder snapshot-dependencies/ ./                                                                                     0.7s
 => [stage-1 4/5] COPY --from=builder spring-boot-loader/ ./                                                                                        0.7s
 => [stage-1 5/5] COPY --from=builder application/ ./                                                                                               0.8s
 => exporting to image                                                                                                                              1.5s
 => => exporting layers                                                                                                                             1.3s
 => => writing image sha256:54238d2394d6175527182519752a1c54eee2a297f7edc0ee6f8013dd7434be85                                                        0.0s
 => => naming to docker.io/arunstiwari/payment-service:1.0
```

+ Start the docker container using the following command
```shell
╭─ ~/training/react/pe/ecommerce/payment-service   feature/04-payment-order-association ● ?                                       ✔  1264  12:14:28
╰─ docker run --rm --name payment-service -p8950:8950 arunstiwari/payment-service:1.0

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.5)

2021-05-13 06:44:53.741  INFO 1 --- [           main] c.t.p.PaymentServiceApplication          : Starting PaymentServiceApplication v0.0.1-SNAPSHOT using Java 16.0.1 on 461f02027dae with PID 1 (/BOOT-INF/classes started by root in /)
...
```
---
### Registering with the Discovery Service
+ Add the following dependencies in the `pom.xml`
```xml
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
</dependencyManagement>
```
+ Add the following properties in the `pom.xml`
```xml
 <spring-cloud.version>2020.0.2</spring-cloud.version>
```
+ Add the following dependency in the `pom.xml`
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

+ Next we go and add the following annotation to the `PaymentServiceApplication` class
```java
@SpringBootApplication
@EnableEurekaClient
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
```
+ After that we add the following properties to the `application.yml` file
    + we add `application name` and `eureka` properties as shown below
```yaml
server:
  port: 8950
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:payments
  application:
    name: PAYMENT-SERVICE
eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: localhost
```
+ Next we start the application and we can see on the `Eureka dashboard` the service registered with it