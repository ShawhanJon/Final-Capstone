<template>
<div>
  <div class="infosec">
    <h1 v-for="cat in catsArray" v-bind:key="cat.catId">{{ cat.name }}</h1>
    <div class="userDetail"> <!-- START OF DETAILS -->

      <!-- USER BIO SECTION WITH PICTURE AND INFORMATION -->
      <div class="userbio" v-for="cat in catsArray" v-bind:key="cat.catId">
        <img class="detailImage" v-bind:src="getImageURL(cat.imageName)" />
        <div class="desc">
          <div><strong>Breed:</strong> {{ cat.breed }}</div>
          <div><strong>Color:</strong> {{ cat.color }}</div>
        </div>
        <div class="occu">
          <strong>Occupation: </strong>{{ cat.occupation }}
        </div>
      </div>

      <!-- LIVES REMAINING SECTION WITH TOMBSTONE -->
      <div class="lives" v-for="cat in catsArray" v-bind:key="cat.catId">
        <img class="tomb" src="../Assets/tombstone.png" style="width: 75%" />
        <div class="dLives">
          <h2 class="hLR">Lives </h2>
            <h2 class="hLR2">Remaining</h2>
          <h1 class="dLL">{{ cat.lives }}</h1>
        </div>
      </div>

      <!-- RIGHT SIDE WITH RATING, LAST SEEN, AND ABOUT ME -->
      <div class="rightside">
        <div class="rating">
          <h3 class="rat">RATing</h3>
          <div class="mice">
            <img class="mice1" src="../Assets/mice-icon-10.jpg" />
            <img class="mice2" src="../Assets/mice-icon-10.jpg" />
            <img class="mice3" src="../Assets/mice-icon-10.jpg" />
            <img class="mice4" src="../Assets/mice-icon-10.jpg" />
            <img class="mice5" src="../Assets/mice-icon-10.jpg" />
          </div>
        </div>

        <div class="placeFrequented" v-for="cat in catsArray" v-bind:key="cat.catId">
          <h3 class="last">Last Seen</h3>
          <p Class="last">{{ cat.address }}</p>
        </div>

        <div class="summaryDetail" v-for="cat in catsArray" v-bind:key="cat.catId">
          <h3 class="more">More About Me</h3>
          <p class="more">{{ cat.summary }}</p>
        </div>
      </div> 
    </div><!-- END OF DETAILS -->

    <div class="msgCont">
      <!-- SEND MESSAGE -->
      <div class="send">
        <send-message></send-message>
      </div>
      <!-- RECEIVE MESSAGE -->
      <div class="receive" v-if="this.$store.state.token != '' ">
        <receive-message></receive-message>
      </div>
    </div>

    <div class="dNe">
    <!-- <div>
      <router-link :to="{ name: 'addcat' }">Edit</router-link>
    </div> -->
    <!-- DELETE BUTTON -->
    <div role="alert" v-if="registrationErrors">
      {{ registrationErrorMsg }}
    </div>
    <div v-for="cat in catsArray" v-bind:key="cat.catId">
      <button type="submit" v-on:click="deleteCat" class="deleteButton" value="Delete Profile">Delete This Profile</button>
    </div>
    </div>
  </div>
</div>
</template>

<script>
import ReceiveMessage from "../components/ReceiveMessage.vue";
import SendMessage from "../components/SendMessage.vue";
import catService from "@/services/CatService.js";

export default {
  name: "user-details",
  components: {
    ReceiveMessage,
    SendMessage,
    
  },
  props: {
    catId: Number,

  },

  data() {
    return {
      catsArray: [],
      registrationErrors: false,
      registrationErrorMsg: 'There were problems deleting this user.',
    };
    
  },

  methods: {
    isEmpty(obj) {
      return Object.keys(obj).length === 0; 
    },
    viewCatDetails() {
      this.$router.push("/catdetails");
    },
    getImageURL(pic) {
      return require("../Assets/CatUsers/" + pic);
    },

    deleteCat() {
      const catId = this.$store.state.catId;

      catService
        .deleteCat(catId)
        .then((response) => {
          console.log(response.status);
          if (response.status == "204") {
            this.registrationErrors = true;
            this.$router.push("/");
          }
        })
        .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
  },
  },
  created() {
    const catId = this.$store.state.catId;

    catService
      .getCatDetails(catId)
      .then((response) => {
        console.log(response);
        console.log(response.data);

        this.catsArray = response.data;
      })
      .catch((error) => {
        console.log(error.statusMsg);
      });
  },
};



</script>

<style scoped>
  /* PAGE STYLING */
.infosec {
  display: flex;
  flex-direction: column;
  text-align: center;
}

  /* DELETE BUTTON */
button[type="submit"] {
  background-color: white;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 7.313%;
  height: 2%;
  top: 2em;
  right: 5em;
}
button[type="submit"]:hover {
  background-color: #ff0000;
  color: #163da1;
}
/* NAME */
h1 {
  font-family: "Ledger", serif;
  font-size: 3rem;
}
.rightside > .rating > h3 {
  font-size: 30px;
}
.rightside > .placeFrequented > h3 {
  font-size: 30px;
}
.rightside > .summaryDetail > h3 {
  font-size: 30px;
}

  /* DETAILS SECTION */
.desc, .mice {
  display:flex;
  flex-direction: row;
  justify-content: space-between;
  margin-left: 20px;
  margin-right: 20px;
  font-family: "Ledger", serif;
}
.rat {
  margin-bottom: 20px;
}
.mice {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: auto 5px auto 5px;
}
.mice1, .mice2, .mice3, .mice4, .mice5 {
  display: block;
  width: 20% !important;
  height: 20%;
  margin-bottom: 15px;
}
.detailImage {
  max-width: 75%;
  justify-content: center;
  height: auto;
  margin-top: 20px;
  margin-bottom: 20px;
  border-radius: 5px;
  margin-left: auto;
  margin-right: auto;
}
.userbio {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 33.33%;
  border-style: outset;
  box-shadow: 0px 0px 2px 2px #163da1;
  border-radius: 10px;
  margin-left: 30px;
  justify-content: flex-start;
  text-align: center;
  font-size: 25px;
}
.lives {
  display: flex;
  flex-direction: row;
  justify-content: center;
  text-align: center;
  width: 33.33%;
  position: relative;
  margin-left: 25px;
}
.dLives {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  height: auto;
  background: transparent;
}
.lives > .dLives > h2 {
  font-size: 30px;
}
.lives > .dLives > h1 {
  font-size: 40px;
}
.hLR, .hLR2, .dLL, .occu, .rat, .last, .more{
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 20px;
  margin-top: 15px;
  background: transparent;
  display: flex;
  flex-direction: column;
  font-family: "Ledger", serif;
  font-size: 18px;
}
.rightside {
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: space-between;
  align-self: center;
  width: 33.33%;
  margin-right: 30px;
  margin-left: 30px;
}
.placeFrequented {
  display: flex;
  flex-direction: column;
  text-align: center;
  margin-bottom: 20px;
  border-style: outset;
}
.summaryDetail {
  display: flex;
  flex-direction: column;
  text-align: center;
  border-style: outset;
}
.placeFrequented, .summaryDetail, .rating {
  display:flex;
  flex-direction: column;
  text-align: center;
  margin-bottom: 20px;
  border-style: outset;
  box-shadow: 0px 0px 2px 2px #163da1;
  border-radius:10px;
  font-family: "Ledger", serif;
}
.userDetail {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.msgCont {
  display: flex;
  flex-direction: column;
  align-items: space-evenly;
  align-content: space-evenly;
  justify-content: space-evenly;
  background: transparent;
  margin: auto;
}
.receive, .send {
  display: flex;
  flex-direction: column;
  align-self: space-evenly;
  justify-content: space-evenly;
  background: transparent;
  align-content: space-evenly;
  background: transparent;
  
}

</style>