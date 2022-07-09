import {expect} from "chai"
import sayHello from "index"

describe("index test", () => {
    describe("sayHello function", () => {
        it("should say Hello Node!", () => {
            const str = sayHello()

            expect(str).to.equal("Hello Node!")
        })
    })
})