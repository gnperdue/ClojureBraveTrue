var node3 = {
    value: "last",
    next: null
};

var node2 = {
    value: "middle",
    next: node3
};

var node1 = {
    value: "first",
    next: node2
};

// We can perform three core functions on a linked list: `first`, `rest`, and
// `cons` (adds a new node with a value to the front of the list). With those
// functions in place, we can implement `map`, `reduce`, `filter`, and other
// seq functions.

// `first` and `rest` operate on one node at a time.
var first = function(node) {
    return node.value;
};

// `first` and `rest` operate on one node at a time.
var rest = function(node) {
    return node.next;
};

var cons = function(newValue, node) {
    return {
        value: newValue,
        next: node
    };
};

print(first(node1));
print(first(rest(node1)));
print(first(rest(rest(node1))));

var node0 = cons("new first", node1);
print(first(node0));
print(first(rest(node0)));

// We may implement `map` in terms of `first`, `rest`, and `cons`.
// This function transforms the first element on the list and then calls itself
// on the rest until it reaches the end (a `null`).
var map = function(list, transform) {
    if (list === null) {
        return null;
    } else {
        return cons(transform(first(list)), map(rest(list), transform));
    }
};

print(first(map(node1, function(val) {
    return val + " mapped!";
})));
print(first(rest((map(node1, function(val) {
    return val + " mapped!";
})))));

// here's how these functions might work for an array:
var first = function(array) {
    return array[0];
};
var rest = function(array) {
    var sliced = array.slice(1, array.length);
    if (sliced.length === 0) {
        return null;
    } else {
        return sliced;
    }
};
var cons = function(newValue, array) {
    return [newValue].concat(array);
};

var list = ["Transylvania", "Forks, WA"];
print(map(list, function(val) {
    return val + " mapped!";
}));
