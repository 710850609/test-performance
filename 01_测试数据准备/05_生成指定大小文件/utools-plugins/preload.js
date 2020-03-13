// 开发者可以暴露自定义API供后加载脚本使用
  
// preload.js 中使用nodejs
const { readFileSync } = require('fs')
const fs = require('fs')
const path = require('path')
const os = require('os');
const child_process = require('child_process')

// index.html 后加载的内容可以使用window.readConfig()方法，但不能使用Node.js特性

/**
 * 生成指定大小的文件
 * @param {*} filePath  文件名绝对路径
 * @param {*} size      文件大小，单位: byte 
 */
window.createFixedFile = function (fileName, size) {
    var tmpFile = path.join(`${os.tmpdir()}`, `${new Date().getTime()}.tmp`)
    var cmd = getCreateFileCmd(tmpFile, size)
    child_process.execSync(cmd)
    this.console.log('创建文件 ' + cmd)
    var type = fileName.substr(fileName.lastIndexOf('.') + 1)
    var targetFileName = new Date().getTime() + '.' + type;
    var targetFile = path.join(`${os.tmpdir()}`, `${targetFileName}`)
    cmd = `copy /b ${fileName} + ${tmpFile} ${targetFile}`
    child_process.execSync(cmd)
    this.console.log('合并 ' + cmd)
    deleteFile(tmpFile)
    // child_process.exec(`explorer.exe /select,"${targetFile}"`)
    utools.copyFile(targetFile)
    return targetFile
}

/**
 * 生成指定大小的文件
 * @param {*} filePath  文件名绝对路径
 * @param {*} size      文件大小，单位: byte 
 */
function getCreateFileCmd (filePath, size) {
    // http://nodejs.cn/api/process/process_platform.html
    switch (process.platform) {
        case 'win32':
            return `fsutil file createnew ${filePath} ${size}`
        case 'aix':
        case 'darwin':
        case 'freebsd':
        case 'linux':
        case 'openbsd':
        case 'sunos':
        default:
            throw new Error('暂不支持当前系统')
    }
}

/**
 * 删除文件
 * @param {*} filePath 
 */
function deleteFile (filePath) {
    fs.access(filePath, fs.constants.F_OK, (exists) => {
        if (!exists) {
            try {
                fs.unlinkSync(filePath);
                console.log(`已成功删除 ${filePath}`);
            } catch (err) {
                throw err
            }
        }
    });
}