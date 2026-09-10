export var javaMdFiles = [];

export function getAllJavaMd() {
    return fetch("/markdown-files")
        .then(res => res.json())
        .then(mdFiles => {
            javaMdFiles = mdFiles;
            return javaMdFiles;
        })
}