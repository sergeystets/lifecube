import axios from 'axios'

const AXIOS = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL + "/api",
  timeout: 10000
})

export default {

  searchSubmissions(filter) {
    console.log("Looking for submissions by filter: " + JSON.stringify(filter));
    return AXIOS.get("/v1/submission/search?" +
        (filter.text ? "text=" + filter.text : "") +
        "&size=" + filter.size +
        (filter.sort ? ("&property=" + filter.sort + "&direction=" + (filter.descending ? "desc" : "asc")) : "") +
        "&page=" + filter.page);
  },

  addNewSubmission(submission, token) {
    console.log("Adding new submission: " + JSON.stringify(submission))
    return AXIOS.post("/v1/submission", submission, {
      headers: {
        'Recaptcha-Token': token
      }
    });
  }
}