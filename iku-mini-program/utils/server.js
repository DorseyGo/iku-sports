const request  = require("./request");

const chkSession = (failcallback = () => {} ) => {
    wx.checkSession({
        success: (result) => {
          console.log("Already login")
        },
        fail: () => {
            failcallback()
        }
      })
}

module.exports = {
    chkSession
}