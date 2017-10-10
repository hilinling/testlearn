/**
 * Created by ling on 17/9/30.
 */
var a = [];
for(let i=0;i<3;i++){
    a[i] = function () {
        console.log(i);
    }
}
a[2]();

