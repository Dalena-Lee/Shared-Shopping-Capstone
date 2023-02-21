<template>
    <main class="account-main-container">
        <div class="groups-container">
            <div class="group-menu-container">
                <h1 class="group-head">Groups</h1>
                <h1 class="group-head message" v-if="!groups.length">You currently have no groups.</h1>
                <div
                v-else
                class="group-menu" 
                v-for="group in groups" 
                v-bind:key="group.groupID" 
                v-bind:group="group">
                    <label>
                      <input 
                      type="radio" 
                      v-bind:value="group.groupID" 
                      class="radio" 
                      v-model="selectedGroupID"
                      v-on:change.prevent="updateGroupContent"
                      >
                      <p>{{ group.groupName }}</p>
                    </label>
                </div>
                <create-group></create-group>
                <create-list></create-list>
            </div>

            <div class="main-content-container">
                <div class="main-content-header">
                    <div class="get-started" v-if="!groups.length">
                        <h1 class="no-content-head-name">Get started by creating a group!</h1>
                    </div>
                    <div
                    v-else
                    v-for="group in findGroup"
                    v-bind:key="group.groupID" 
                    >
                    <h1 class="content-head-name">{{ group.groupName }}</h1>
                        <nav role="navigation">
                            <div id="menuToggle">
                                <input type="checkbox" />
                                <span class="hamburger"></span>
                                <span class="hamburger"></span>
                                <span class="hamburger"></span>
                                <ul id="menu">          
                                    <li><a href="invite-people-form">
                                        <p><span class="material-symbols-outlined">group_add</span>Invite People</p>
                                        </a>
                                    </li>
                                    <a href="edit-group-form"><li><p><span class="material-symbols-outlined">edit</span>Edit Group</p></li></a>
                                    <a href="delete-group-form"><li><p><span class="material-symbols-outlined">delete</span>Delete Group</p></li></a>
                                </ul>
                            </div>
                        </nav>
                    </div>
                    
                </div>
                <div class="main-content">
                    <input id="lists" type="radio" name="tabs" checked>
                    <label for="lists"><h1 class="content-label">Lists</h1></label>
                    <input id="members" type="radio" name="tabs">
                    <label for="members"><h1 class="content-label">Members</h1></label>
                    <a class="list-button" href="#create-list-form"><h5>+ Create new list</h5></a>
                    <div id="create-list-form" class="overlay2">
                        <div class="form2">
                            <h1>Create new list</h1>
                            <a class="close" href="#">&times;</a>
                            <div class="content">
                                <form>
                                    <div>
                                        <label for="listName"><p>List name</p></label>
                                        <input type="text" name="listName" placeholder="Name your list" v-model="newList.listName"/>
                                    </div>
                                    <button class="actions" type="submit" v-on:click.prevent="saveList()">
                                        <p>Create</p>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                       
                    <section id="list-content">
                        <table>
                            <thead>
                                <tr class="list-headings">
                                    <th class="active">
                                        <h1>Actively</h1>
                                        <h1>Shopping</h1>
                                    </th>
                                    <th>
                                        <h1 style="margin-right: 60px;">List</h1>
                                    </th>
                                    <th>
                                        <h1>By User</h1>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr
                            v-for="list in lists"
                            v-bind:key="list.listId"
                            v-bind:list="list"
                            v-bind:class="{ activeList: list.listId === 'selectedList.listId' }"
                            >
                                <td>
                                    <input 
                                    type="checkbox" 
                                    v-bind:id="list.listId" 
                                    v-bind:value="list.listId" 
                                    v-model="selectedListIDs" 
                                    style="display: inline-block; width: 80px;"
                                    />
                                </td>
                                <td>
                                    <p>{{list.listName}}</p>
                                </td>
                                <td>
                                    <select>
                                        <option value>Select User</option>
                                        <option 
                                        v-for="user in users"
                                        v-bind:key="user.id"
                                        v-bind:user="user"
                                        value>{{user.username}}</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                        </table>
                    </section>

                    <section id="member-content">
                        <table>
                            <tbody>
                                <tr
                                v-for="user in users"
                                v-bind:key="user.id"
                                v-bind:user="user"
                                >
                                    <td>
                                        <p>{{user.username}}</p>
                                    </td>
                                </tr> 
                            </tbody>
                        </table>
                    </section>
                </div>
            </div>
        </div>
        <div class="lists-container">
            <h1 class="list-head">Active Shopping List</h1>
    
            <table class="item-table">
                <tr
                v-for="item in itemList"
                v-bind:key="item.itemId"
                >
                <td>
                    <input type="checkbox" id="items" v-bind:value="item.itemId"/>
                </td>
                <td>
                    <h2>{{ item.item }}</h2>
                </td>
                </tr>
            </table>
        </div>
    </main>  
</template>

<script>
import ItemService from '../services/ItemService';
import CreateGroup from '../components/CreateGroup'
import CreateList from '../components/CreateList.vue'
import GroupService from '../services/GroupService';
import ListService from '../services/ListService';

export default {
    name: "account-main",
    data () {
        return {
            groups: [],
            lists: [],
            users: [],
            items: [],
            selectedGroupID: 330,
            selectedListID: 5,
            activeLists: [],
            newList: {
                listName: "",
            },
        }
    },
    components: {
        CreateGroup,
        CreateList
    },
    methods: {
        updateGroupContent() {
            ListService.getLists(this.selectedGroupID).then(response => {
            this.lists = response.data;
            });

            GroupService.getUsers(this.selectedGroupID).then(response => {
            this.users = response.data;
            });
        },
        updateItems() {
            ItemService.getItems(this.selectedListID).then(response => {
                this.items = response.data;
            })
        },
        saveList() {
            const newList = {
                listName: this.listName
            }

            let groupId = this.selectedGroupID;

            ListService.createList(newList, groupId)
            .then(response => {
                if (response.status === 201) {
                    window.location.reload();
                }
            })
        }
    },
    computed: {
        findGroup() {
            let selectedGroup = this.groups.filter( (group) => {
                return this.selectedGroupID === group.groupID;
            });

            return selectedGroup;
        },
        activeList() {
            let filteredList = this.lists;
                filteredList = filteredList.filter((list) => {
                    return this.selectedListID === list.listID;
                })
            return filteredList;
        },
        selectedLists() {
            let activeLists = this.lists;
            activeLists = activeLists.filter(( list ) => {
                return this.selectedListID === list.listId;
            });
            return activeLists;
        },
        itemList() {
        let items = this.items;
            items = items.filter((item) => {
                return this.selectedListID === item.listId;
            });
            return items;
      }
    },
    created() {
        GroupService.getMyGroups().then(response => {
            this.groups = response.data;
        });

        GroupService.getUsers(this.selectedGroupID).then(response => {
            this.users = response.data;
        });

        ListService.getLists(this.selectedGroupID).then(response => {
            this.lists = response.data;
        });

        ItemService.getItems(this.selectedListID).then(response => {
            console.log(response);
            this.items = response.data;
        })
    }
}
</script>

<style>
.account-main-container {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr;
    grid-row: 1 / 4;
    grid-column: 1 / 4;
    background: #eeeeee;    
}

.groups-container  {
    display: grid;
    grid-template-columns: 250px 1fr 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr;
    background:white;
    grid-column: 1 / 4;
    grid-row: 1 / 4;
    border-radius: 20px;
    border-style: none solid none solid;
    border-color: #3DC795;
    border-width: 5px;
    box-shadow: 0 14px 28px rgba(0,0,0,0.50);
    margin: 20px 400px 200px 275px;
}

.group-menu-container {
    grid-column: 1;
    grid-row: 1 / 4;
    border-radius: 20px 0px 0px 20px;
    border-style: none solid none none;
    border-width: 1px;
    border-color: lightgray;
    background: rgb(248, 248, 248);
}

.group-head {
    text-align: center;
    margin-top: 40px;
    font-family: "Gelion";
    color: #3DC795;
    font-size: 2rem;
}

.group-head.message {
    font-size: 18px;
    padding: 20px 60px 0 60px;
    margin: 0;
    color: rgb(121, 121, 121);
}

.group-menu {
    grid-column: 1;
    grid-row: 1 / 4;
    margin-left: 40px;
    margin-top: 75px;
    margin-top: 40px;
}

.radio {
    display: none;
}

.group-menu p {
    text-align: left;
    font-size: 18px;
}

.group-menu label:hover {
    color: #3DC795;
    cursor: pointer;
}

.main-content-container {
    grid-column: 2 / 5;
    grid-row: 1 / 4;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 150px 1fr 1fr 1fr;
}

.main-content-header {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: 1fr;
    grid-column: 1 / 5;
    grid-row: 1;
    font-family: "Gelion";
    background: #3DC795;
    border-radius: 0px 20px 0 0;
}

.main-content-container nav {
    grid-row: 3;
    grid-column: 3;
}

.content-head-name {
    margin-left: 20px;
    margin-top: 95px;
    text-align: left;
    grid-row: 1;
    grid-column: 1 / 4;
    font-family: "Gelion";
    color:white;
    font-size: 2rem;
}

.no-content-head-name {
    margin-left: 20px;
    text-align: left;
    grid-row: 1;
    grid-column: 1 / 3;
    font-family: "Gelion";
    color:white;
    font-size: 2rem;
}

.get-started {
    grid-row: 1;
    grid-column: 1 / 3;
    margin-top: 50px;
}

.list-button {
    grid-row: 1;
    grid-column: 3;
    margin-left: 40px;
    margin-top: 8px;
    color: #3DC795;
}

#menuToggle{
  grid-row: 3;
  grid-column: 3;
  top: -30px;
  left: 420px;
  display: block;
  position: relative;
  z-index: 1;
  -webkit-user-select: none;
  user-select: none;
}

#menuToggle a
{
  text-decoration: none;
  color: white;
  transition: color 0.3s ease;
}


#menuToggle input
{
  display: block;
  width: 40px;
  height: 32px;
  position: absolute;
  cursor: pointer;
  opacity: 0; 
  z-index: 2; 

  -webkit-touch-callout: none;
}

#menuToggle span.hamburger{
  display: block;
  width: 33px;
  height: 4px;
  margin-bottom: 5px;
  position: relative;
  background:white;
  border-radius: 3px;
  transform-origin: 4px 0px;
  transition: transform 0.5s cubic-bezier(0.77,0.2,0.05,1.0),
              background 0.5s cubic-bezier(0.77,0.2,0.05,1.0),
              opacity 0.55s ease;
}

#menuToggle span.hamburger:first-child
{
  transform-origin: 0% 0%;
}

#menuToggle span.hamburger:nth-last-child(2)
{
  transform-origin: 0% 100%;
}

#menuToggle input:checked ~ span
{
  opacity: 1;
  transform: rotate(45deg) translate(-2px, -1px);
}

#menuToggle input:checked ~ span:nth-last-child(3)
{
  opacity: 0;
  transform: rotate(0deg) scale(0.2, 0.2);
}

#menuToggle input:checked ~ span:nth-last-child(2)
{
  transform: rotate(-45deg) translate(0, -1px);
}

#menu {
  position: absolute;
  width: 150px;
  margin: 15px 0 0 -150px;
  padding: 20px;
  border-radius: 0 0 20px 20px;
  border-style: solid;
  border-width: 0px 5px 5px 0px;
  border-color: rgb(68, 155, 122);
  background: #3DC795;
  list-style-type: none;
  -webkit-font-smoothing: antialiased;
  visibility: hidden;  
  transform: translateY(-100px, 0);
}

#menu li {
  padding: 10px 0;
  font-family: "Gelion";
  font-size: 18px;
  font-weight: 500;
  text-align: left;
}

#menu li:hover {
    text-decoration: underline;
}

#menuToggle input:checked ~ ul
{
  transform: none;
  visibility: visible;
}


span.material-symbols-outlined {
    margin-right: 10px;
}

.main-content {
    display: grid;
    grid-template-columns: 100px 1fr 200px;
    grid-template-rows: 40px 1fr 1fr;
    padding: 20px;
    grid-column: 1 / 5;
    grid-row: 2 / 5;
    border-width: 1px;
    border-color: lightgray;
    border-style: none none solid none;
}

.main-content h1 {
    font-family: "Gelion";
    color: #3DC795;
    font-size: 1.5rem;
}

.main-content a > p {
    color: #3DC795;
    font-size: 18px;
    text-align: center;
}

.list-button {
    margin-right: -50px;
}

.main-content p {
    font-size: 18px;
}

.main-content input {
    clear: both;
    padding-top: 10px;
    display: none;
}

.main-content section {
    display: none;
    clear: both;
    grid-column: 1 / 4;
    grid-row: 2 / 4;
    border-color: lightgray;
    border-width: 1px 0 0 0;
    border-style: solid;
}

.main-content label {
    display: block;
    float: left;
    border-top: 2px solid transparent;
    border-right: 1px solid transparent;
    border-left: 1px solid transparent;
    border-bottom: 0px solid #DDD;
    margin-left: 30px;
}
.main-content label:hover {
    cursor: pointer;
    color:  #3DC795;
}

.main-content #lists:checked ~ #list-content, #members:checked ~ #member-content {
    display: block;
}
.main-content input:checked + label {
    border-bottom-color: #3DC795;
    text-decoration: none;
    border-width: 2px;
    color: #3DC795;
}

.lists-container {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr;
    background:white;
    grid-column: 3 / 5;
    grid-row: 1 / 4;
    border-radius: 20px;
    border-style: none solid none solid;
    border-color: #3DC795;
    border-width: 5px;
    box-shadow: 0 14px 28px rgba(0,0,0,0.50);
    margin: 20px 275px 200px 100px;
    overflow-y: scroll;
}

.list-container #items:checked {
  text-decoration: line-through;
}

.item-table {
    grid-column: 1 / 5;
    grid-row: 1 / 4;
    margin-top: 140px;
    text-align: left;
    font-family: "Gelion";
    font-size: 14px;
    margin-right: 100px;
    margin-left: 100px;
}

.list-head {
    grid-column: 2 / 4;
    grid-row: 1;
    text-align: center;
    margin-top: 40px;
    font-family: "Gelion";
    color: #3DC795;
    font-size: 2rem;
}
</style>
