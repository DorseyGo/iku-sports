//index.js
//获取应用实例
const app = getApp();
const request  = require("../../utils/request");

const def_activities = [
  {
    id: 1,
    image: '../../images/basketball_2.png'
  },
  {
    id: 2,
    image: '../../images/tennis_2.png'
  }
]

const def_categories = [
  {
    id: 1,
    avatar: '../../images/basketball.png',
    displayName: '篮球'
  },
  {
    id: 2,
    avatar: '../../images/tennis.png',
    displayName: '网球'
  }
]

Page({
  data: {
    activities: [],
    categories: [],
    current: 0,
    teachingStyles: []
  },

  onLoad: function () {
    /** request to fetch activities */
    request.get(`activities`).then(res => {
      this.setData({
        activities: res.data,
        current: 0
      })

      this.setDefActivitiesIfPossible()
    }, reason => {
      console.log("Failed to request for activities")
      this.setDefActivitiesIfPossible()
    })

    request.get(`categories`).then(res => {
      this.setData({
        categories: res.data
      })

      this.setDefCategoriesIfPossible()
    }, reason => {
      console.log("Failed to request for categories")
      /* if rejected, set the defaults */
      this.setDefCategoriesIfPossible()
    })

    request.get(`teaching-styles`).then(res => {
      this.setData({
        teachingStyles: res.data
      })
    })
  },

  setDefActivitiesIfPossible: function() {
    if (this.data.activities.length == 0) {
      this.setData({
        activities: def_activities
      })
    }
  },

  setDefCategoriesIfPossible: function() {
    if (this.data.categories.length == 0) {
      this.setData({
        categories: def_categories
      })
    }
  }
})
