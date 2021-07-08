import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "Should return customer by id=1"

    request {
        url "/customers/1"
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
