import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "Should return list of customers"

    request {
        url "/customers"
        method GET()
    }

    response {
        status(OK())
        headers {
            contentType applicationJson()
        }
        body(
                id: 1,
                name: "Customer-1"
        )
    }
}
