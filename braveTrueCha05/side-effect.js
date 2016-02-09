var haplessObject = {
    emotion: "Carefree!"
};

var evilMutator = function(object) {
    object.emotion = "So emo :'(";
};

print(haplessObject.emotion);
evilMutator(haplessObject);
print(haplessObject.emotion);
