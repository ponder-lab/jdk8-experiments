
function filter(f, xs) {
	var len = xs.length;
	var res = [];
	for(var i=0; i < len; i++){
		var val = xs[i];
		if ( f(val) ){
			res.push(val);
		}
	}
	return res;
}

function map(f, xs) {
	var len = xs.length;
	var res = [];
	for(var i=0; i < len; i++){
		var val = xs[i];
		res.push(f(val));
	}
	return res;
}

function reduce(f, acc, xs) {
	var len = xs.length;
	for(var i=0; i < len; i++) {
		var val = xs[i];
		acc = f(acc,val);
	}
	return acc;
}


var numbers = [1,2,3,4,5];
var evens = reduce(function(x,y){
	return x * y ;
}, 1, numbers);

console.log(evens);