//example:
// window.onload = () => {
//     const r = new Rider(
//         {
//             mount: "#idiv",
//             ele: ["div,class=k", "div,id=a"],
//             //ele: "div,class=k",
//             content: ["js", "hap"]
//             //content: "hello,world",
//         }
//     )

// }
class Rider {
    constructor(element) {
        this.mount = document.querySelector(element.mount);
        if (typeof (element.ele) === "string") {
            this.eleList = element.ele.split(",");
            this.newEle = this.newNode(
                this.eleList[0],
                this.eleList[1].split("=")[0],
                this.eleList[1].split("=")[1]
            );
            this.newEle.innerText = element.content;
            this.mount.append(this.newEle);
        } else if (typeof (element.ele) === "object") {
            for (let i = 0; i < element.ele.length; i++) {
                this.eleList = element.ele[i].split(",");
                this.newEle = this.newNode(
                    this.eleList[0],
                    this.eleList[1].split("=")[0],
                    this.eleList[1].split("=")[1]
                );
                this.newEle.innerText = element.content[i];
                this.mount.append(this.newEle);
            }
        }
    }
    newNode(elename, attriName, attriValue) {
        let ne = document.createElement(elename);
        ne.setAttribute(attriName, attriValue);
        return ne;
    }
}


