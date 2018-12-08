regExps = {
"exercise_1": /([A-Z][a-z]+)/,
"exercise_2": /(088[1-9]{1}[0-9]{6})/,
"exercise_3": /([^0-1]+)/,
"exercise_4": /(^[a-z][^\@\$]{2,13}$)/,
"exercise_5": /((^[0-9]{1,3}$)|([1][0-4][0-9][0-9]$)|1500)/,
"exercise_6": /(class=["'].*["'])/
};
cssSelectors = {
"exercise_1": "item > java.class1",
"exercise_2": "different different .diffClass",
"exercise_3": "item java tag.class1.class2",
"exercise_4": "css > item:nth-child(3)",
"exercise_5": "tag.class1.class2 java.class1:nth-child(2)",
"exercise_6": "item#someId item item item item",
"exercise_7": "css > different > different:nth-child(2) > java:nth-child(2)",
"exercise_8": "#someId"

};
