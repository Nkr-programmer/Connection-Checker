console.log("this is scripting")
const toggleSidebar =()=>{
    if($('.sidebar').is(":visible")){
        $('.sidebar').css("display","none");
        $('.content').css("margin-left","0%");
    }
    else{
        $('.sidebar').css("display","block");
        $('.content').css("margin-left","20%");
    }

};

const search =(imagePrefix)=>{
    let query=$("#search-input").val();
    let filter=$("#contactFilter").val();
    console.log(filter);
    console.log(imagePrefix);
    if(query == ''){$(".search-result").hide();}
    else{
        console.log(query);
        let url=`http://localhost:8080/search/${query}/${filter}`;
        fetch(url).then(
            (response)=>{return response.json();}
        ).then(
            (data)=>{
                console.log("DATA",data);
                let filtertype=filter;
                if(filtertype==="Name"){ filtertype="name";}
                else if(filtertype=="Email"){filtertype="email";}
                else if(filtertype==="SubCompany"){filtertype="subcompany";}
                else if(filtertype==="Company"){filtertype="companies";}
                else if(filtertype==="Projects"){filtertype="projects";}
                else if(filtertype==="TechStack"){filtertype="techstack";}

            let text=`<div class='list-group'>`;
            data.forEach((contact) => {
                text +=`<a href='/user/${contact.c_Id}/contact' class='list-group-item list-group-item-action'><img class="my-profile-pic" src="${(contact.image =='contacts.png') ? '/img/contacts.png' : '/img/'+imagePrefix+contact.c_Id+contact.image }">  ${contact.name} , <b>${filter}</b> ${contact[filtertype]}</a>`
            });
            text +=`</div>`;
            $(".search-result").html(text);  
            $(".search-result").show();  
            }
            );  
    }

};

const displaycompanies =()=>{
    let val=$("#companies").val();
   // document.getElementById('companies').value = ''
    if(val == ''){
        $('.descbox').css("margin-top","17px");
        $(".companies-result").hide();}
    else{
        console.log(val);
        
            let text=`<div class='list-group'>`;
                text +=`${val}`
            text +=`</div>`;
            $(".companies-result").html(text);  
            $('.descbox').css("margin-top","81px");
            $(".companies-result").show(); 
             
    }
}
//<td><img src="" th:if="${c.image}" class="my-profile-pic" th:src="@{'/img/'+${c.image}}" alt="image"><span th:text="${c.name}"></span></td>