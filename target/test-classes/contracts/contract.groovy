import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Testing GetModel End Point"
    request {
        method GET()
        url("/api/v1/service-template/model/1") {
        }
    }
    response {
        body(file("jsons/modelresponse.json"))
        status 200
    }
}