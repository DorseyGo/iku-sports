const request  = require("./request");

const chkSession = (login = () => {}) => {
  /** check whether login expired */
  wx.checkSession({
    success: (result) => {
      console.log("Already login")
    },
    fail: () => {
      login()
    }
  })
}

module.exports = {
  chkSession
}