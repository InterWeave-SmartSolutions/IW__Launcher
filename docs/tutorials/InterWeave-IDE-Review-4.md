# InterWeave IDE Review Zoom Meeting

**Meeting Date:** 5th Mar, 2024 - 10:30 AM

---

**Andrew Magown** *[00:01]*: Drew is still. 
**Andrew Magown** *[00:07]*: I called Drew. Oh, he'll get on in a minute. All right. Drew's coming in. 
**WAHEED QAMAR** *[00:25]*: Hello, sir. How are you? 
**Andrew Magown** *[00:27]*: Good. How are you doing? 
**WAHEED QAMAR** *[00:29]*: I am good, thank you. 
**Andrew Magown** *[00:30]*: Good. 
**Dmytro Zotkin** *[00:32]*: Okay, Bruce, so who are we waiting? 
**Andrew Magown** *[00:36]*: Drew is coming in, but you can go ahead. 
**Dmytro Zotkin** *[00:38]*: Okay. Who has the most stable access to. 
**Andrew Magown** *[00:51]*: Share? 
**Dmytro Zotkin** *[00:53]*: Yeah, I still have the most access. Hang on. Okay, so then let's open the server and. 
**Andrew Magown** *[01:06]*: Let'S start. 
**Dmytro Zotkin** *[01:23]*: Can you open or you want to start? No. 
**Andrew Magown** *[01:27]*: Yeah, well, he'd start for now. 
**Dmytro Zotkin** *[01:30]*: Something weird. Something weird is happening on my side. So start for now and I'll see if I get. All right, well, Drew's coming in too, so. 
**Andrew Magown** *[01:39]*: Why. 
**Dmytro Zotkin** *[01:40]*: You can start the camp then. 
**Andrew Magown** *[01:41]*: Do it then, Drew. 
**WAHEED QAMAR** *[01:42]*: Okay. 
**Andrew Magown** *[01:44]*: All right. 
**Andrew Magown** *[01:47]*: Morning, everyone. Thank you for waiting. 
**Dmytro Zotkin** *[01:50]*: Morning. 
**Andrew Magown** *[01:51]*: Hi, Andrew. 
**WAHEED QAMAR** *[01:53]*: Andrew. 
**Andrew Magown** *[01:54]*: Hello, Dimitri. Hello, Wahid. 
**WAHEED QAMAR** *[01:57]*: How are you? 
**Dmytro Zotkin** *[01:58]*: Actually, it's. 
**Andrew Magown** *[02:04]*: Who cares? 
**Dmytro Zotkin** *[02:06]*: I don't. 
**Andrew Magown** *[02:07]*: I'm doing good, Wahid. Thank you for asking. How are you? 
**Andrew Magown** *[02:11]*: I'm good, thank you, Wahid. Why your RTP is so big? Is it. 
**Andrew Magown** *[02:21]*: Configuring security? 
**WAHEED QAMAR** *[02:22]*: I think my Internet connection. Andrew, can you share your screen? 
**Dmytro Zotkin** *[02:26]*: Yeah, why don't you have your start? 
**Andrew Magown** *[02:27]*: That's fine. Okay. That's going to better because. 
**Dmytro Zotkin** *[02:42]*: One moment. 
**Andrew Magown** *[02:47]*: Okay, very good. So now let's share RDP session with our test server and we'll go from there. 
**Andrew Magown** *[03:03]*: Okay, let me just find the password real fast. Saved. 
**Andrew Magown** *[03:10]*: Here we winter at zero g capital. Perfect. Okay, that's good. 
**Dmytro Zotkin** *[03:28]*: So today we'll look at the flow. One of the flows that kind of will be a subject of further corrections and actual updates because there is a flow which actually almost ready to be. 
**Andrew Magown** *[03:48]*: This meeting is being recorded except for. 
**Dmytro Zotkin** *[03:51]*: The fact that the major transformer is still from the salesforce side. But the flow itself supposed to work with create show. So that part has to be changed and maybe we'll be able to do something about it. Maybe not today, but a little bit later. At this point let's look at the Transformer again and let's try to repeat what we did last time. We open transformer and I will show you which XMl file to open, the one that is supposed to be transformed. And I will try to go through like transformer itself and explain what that transformer is doing and how actually all our transformers are built for that. Andrew, could you please first of all start. 
**Andrew Magown** *[05:01]*: I just don't know what is. 
**Dmytro Zotkin** *[05:06]*: Probably. 
**Andrew Magown** *[05:06]*: We will need to change something, but. 
**Dmytro Zotkin** *[05:08]*: It'S so just let's start id. 
**Andrew Magown** *[05:17]*: While I load. I'm just going to close my door. 
**Andrew Magown** *[05:21]*: That's okay. It. Thank you. 
**Dmytro Zotkin** *[05:49]*: Okay, and let's go to the project which is called sfauthorized. 
**Andrew Magown** *[05:56]*: Net. 
**Dmytro Zotkin** *[05:57]*: This one? 
**Andrew Magown** *[05:58]*: Yeah, just open it and go to integration flows. Expand integration flows. 
**Dmytro Zotkin** *[06:07]*: And then expand queries. And very first query. It's actually the query that's supposed to be used to process payment going from creatio to stripe. Double click at it. Oh, I remember we had it last time when it was not drawing the picture on your computer. 
**Andrew Magown** *[06:39]*: Okay, what can we do here? 
**Dmytro Zotkin** *[06:47]*: You remember we had it last time? Was it fixed or we just switched presenter to someone else? 
**Andrew Magown** *[06:53]*: We switched presenters last time. I believe. 
**WAHEED QAMAR** *[06:56]*: I think you restarted the ide when it was fixed. 
**Dmytro Zotkin** *[07:02]*: Okay, let's try to restart id. No, not this one. 
**Andrew Magown** *[07:06]*: Yeah, just close it and then let's. 
**Dmytro Zotkin** *[07:10]*: Restart it one more time. 
**Andrew Magown** *[07:11]*: Maybe it wants to start it twice. I don't know. 
**Andrew Magown** *[07:16]*: It just likes to be wanted. 
**Andrew Magown** *[07:20]*: Yeah. Oh my goodness. 
**Andrew Magown** *[07:25]*: Okay, let's try this again. Go to SF two author queries. Go to the top one. 
**Dmytro Zotkin** *[07:39]*: Can you try any other flows? No, it's not showing it for you. Just give me 1 second. 
**Andrew Magown** *[07:54]*: Sorry about this. 
**Andrew Magown** *[07:55]*: No, that's okay. 
**Dmytro Zotkin** *[07:57]*: I'm just trying to. Okay, so don't worry, you will lose our DP session. 
**Andrew Magown** *[08:06]*: You will be able to return it. 
**Dmytro Zotkin** *[08:07]*: I just want to open the session and to see if it's on the server side or just on your screen side. 
**Andrew Magown** *[08:15]*: Sure, let me open that server. 
**Andrew Magown** *[08:19]*: All right, I'll close it. 
**Andrew Magown** *[08:21]*: No. 
**Andrew Magown** *[08:23]*: Not that. Just the ide. 
**Dmytro Zotkin** *[08:25]*: No, open id. 
**Andrew Magown** *[08:27]*: Okay. 
**Dmytro Zotkin** *[08:28]*: Because I need to open exactly the screen that you're looking at and see what is. 
**Andrew Magown** *[08:35]*: Copy that. 
**Andrew Magown** *[08:39]*: Okay. And open this screen. Okay. 
**Dmytro Zotkin** *[08:49]*: And keep it like that. 
**Andrew Magown** *[08:51]*: Now let me look at the screen from my screen. Okay. It your picture should be closed soon. Yes. 
**Dmytro Zotkin** *[09:20]*: Okay, so it's something with your screen because it shows on my screen perfectly well. So when I'm looking at my screen, it shows the picture screen settings. 
**Andrew Magown** *[09:35]*: Yeah, I believe we had this issue before with Wahid screen and we did something to alternate it within the actual remote desktop. I'm trying to remember how we did that though. It's not on the top of my head. 
**Dmytro Zotkin** *[09:53]*: See, like within remote desktop you don't need to because when I open, let me open it from another computer. I have my computer. So let me open from this computer the. 
**Andrew Magown** *[10:10]*: Same screen. 
**Dmytro Zotkin** *[10:12]*: And this one is a laptop which. 
**Andrew Magown** *[10:14]*: Has smaller screen, different resolution. Okay, so let me see. Open connect. 
**Andrew Magown** *[10:27]*: Yeah, I'll have to look into this later. Heck is going on? 
**Dmytro Zotkin** *[10:35]*: If I will be able. But your computer is Windows computer, right? 
**Andrew Magown** *[10:40]*: Correct. It's on the latest windows eleven. 
**Andrew Magown** *[10:45]*: Okay. Oh, I'm just trying to. Why it's taking that long to connect. Finally. Okay. 
**Dmytro Zotkin** *[11:11]*: Is someone trying to connect a server right now? 
**Andrew Magown** *[11:15]*: I don't believe so, no. 
**Andrew Magown** *[11:17]*: Okay, let me try one more time. Oh yeah. I cannot connect from this computer. 
**Dmytro Zotkin** *[11:40]*: But in any case we need to share it somehow. So how do we do that? Let me just try to do this. 
**Andrew Magown** *[11:48]*: Could we try on Ian's machine? Would that be possible? 
**IAIN MAGOWN** *[11:52]*: I'm back. Do you want to try mine? 
**Dmytro Zotkin** *[11:54]*: Yes, let's try your computer. 
**IAIN MAGOWN** *[11:57]*: Okay, hang on. 
**Andrew Magown** *[12:02]*: If not, I'll try to do it from my iPad. But sharing an iPad requires a lot of. 
**Dmytro Zotkin** *[12:12]*: It's not kind of very good and I won't be able to do anything. 
**Andrew Magown** *[12:18]*: In parallel so it's not good to have it. 
**Andrew Magown** *[12:22]*: Dimitri, do you know what the actual IDE program name is? If I was like look it up online. 
**Andrew Magown** *[12:32]*: What do you mean? 
**Andrew Magown** *[12:33]*: Well, it's a program. 
**Dmytro Zotkin** *[12:36]*: It's a program that I wrote. 
**Andrew Magown** *[12:39]*: Oh, you wrote it. 
**Dmytro Zotkin** *[12:41]*: It doesn't exist anywhere else. It's been written like in 2007, eight, nine, like three or four years. We were writing it. We were going to sell it. But then eventually we started selling the products of id. It's eclipse. But it won't help because this graphics, it's a graphic component of Java server page. So you can check graphic components like Google graphic components of Java server page or like graphic elements of Java server page are not showing up. I don't know. 
**Andrew Magown** *[13:29]*: Kind of. 
**Andrew Magown** *[13:30]*: Yeah, just something I thought I could look into on the site. 
**Dmytro Zotkin** *[13:34]*: Yeah, but that's where you can find it because that's the mechanism that shows that picture. Okay, Ian, what's the password again? We've entered w. Like interweave but like swapping the party. 
**Andrew Magown** *[13:55]*: It's in the chair. 
**Andrew Magown** *[13:57]*: Thank you. 
**Dmytro Zotkin** *[13:58]*: We've entered at zero g capital. 
**IAIN MAGOWN** *[14:23]*: It. 
**Andrew Magown** *[14:24]*: One moment. 
**Dmytro Zotkin** *[14:34]*: Okay, that's good. That's really nice. So we are looking at the screen which actually was opened by Andrew. So it means that nothing has to be changed inside RDP because screen inside the server is okay for some reason. Maybe RDP client doesn't allow it. Maybe RDP client settings have to be changed. But in any case, let's return back to what we are looking at. So the transactions again like shortly. I already explained what those transactions were doing. The first two transactions are creating connectivity mechanism to create your API. It's a first one and second one and third one actually. Because first we need to authorize ourselves. Then we need to build the session and then we need based on that session or we need to build some authorization proxy and then build the session. 
**Dmytro Zotkin** *[15:52]*: There are three transactions actually are used connecting to create your API if I remember correctly. And then the transaction number four just reads information using those parameters built in the first three transactions reading the information from creator. We will not be looking at them because these are very specific. That's a little bit later kind of. But for transaction number five, no, it's actually the last one. The last transaction actually creates the document which goes to a stripe based on the information read from creative. When we are reading from creative, that's just a command on the API level that has like filtering by the name and those things will discuss a little bit later. Right now we need to see how information which came from, in this case it will come from creative. 
**Dmytro Zotkin** *[16:58]*: It will be a little bit different but very similar to what we will be looking at right now. The transformer like first 12345 transformers already oriented on create only last one is still transformers between Salesforce and stripe. So let's open that transaction. 
**Andrew Magown** *[17:19]*: The last one, no, last one. 
**Dmytro Zotkin** *[17:26]*: Okay. And then click the data map at the very bottom. Double click and then see it is the transformer which is this one. Yes. So click on it and let's see what it will open. It opens this one. So this is the transformer. Now we'll try to open it in external editor. Unfortunately I don't believe it will open. So we'll do it a little bit differently as far as I remember. Have you adjusted vs code to work with XML files? Because XMl spy trial expired already. You cannot open XMl spy anymore. So did you look, is there any XML XSLt plugins to vs. Code? You can open any file in vs code. Actually it will show like UI shows like any file. So we probably will be using that one. Just for sake of kind of experiment. Let's click at the editor icon. 
**Dmytro Zotkin** *[18:51]*: Editor icon at the right, top corner. See this? No, that's not editor. Editor is a little bit down. Yeah, and the rightmost one it shows like here, that's the editor. See it tries to open XML Spy, but XMl Spy doesn't allow you to open it anymore. So that's it's gone. So. 
**WAHEED QAMAR** *[19:22]*: We need to, I think we have added extensions to the visual studio code for XML and. 
**Dmytro Zotkin** *[19:31]*: Okay, so how do we do that? We need to make, we need to make one thing we need to make readjust the type assignment like default editor for Xslt for all XML type XsLT XML. All this Xsl all files which are related to XML should be redirected to this visual source code application. 
**Andrew Magown** *[20:13]*: We'll have to set that in preferences, but I believe if you open visual studio code, there should be an option to set it to an XML file type. 
**Dmytro Zotkin** *[20:26]*: Yeah, let's do that. Let's open the Vs code. Close this one. 
**Andrew Magown** *[20:32]*: Just close this one. 
**Dmytro Zotkin** *[20:33]*: Because this is useless. We need to redirect. So let's open vs code. It's at the bottom, the third from the right. 
**Andrew Magown** *[20:41]*: Taskbar. 
**Dmytro Zotkin** *[20:41]*: Yeah, taskbar the third from the right. 
**Andrew Magown** *[20:46]*: The windows taskbar at the very bottom. 
**Andrew Magown** *[20:49]*: Yeah, this one. 
**Dmytro Zotkin** *[20:53]*: Let's open it and let's try to. 
**Andrew Magown** *[20:55]*: Redirect and let's see if it will help us. 
**IAIN MAGOWN** *[21:01]*: Demetri, are you able to annotate on your side? 
**Dmytro Zotkin** *[21:05]*: Sorry? 
**IAIN MAGOWN** *[21:06]*: Are you able to annotate on your side? Can you use the annotate function on zoom on your side? 
**Dmytro Zotkin** *[21:16]*: To curiosity annotate? No. 
**IAIN MAGOWN** *[21:23]*: Along your toolbars there you'll see the summary AI companion annotate. If you press on that, you'll be able to draw what he did to make the pointing easier. 
**Dmytro Zotkin** *[21:39]*: I don't believe it will work. I can try? 
**Andrew Magown** *[21:42]*: Of course, yeah, give a try. 
**Dmytro Zotkin** *[21:47]*: I am. 
**Andrew Magown** *[21:51]*: Let me see. Because it's safari, I'm not 100% sure that it will. 
**IAIN MAGOWN** *[21:58]*: The zoom functions are all the same, so it should work. 
**WAHEED QAMAR** *[22:04]*: About the mute option. 
**Andrew Magown** *[22:07]*: I don't have it. 
**IAIN MAGOWN** *[22:09]*: No, I don't. 
**Dmytro Zotkin** *[22:11]*: Okay, well, I have mute start video share screen. 
**WAHEED QAMAR** *[22:18]*: It's right here on the screen. If you place your cursor over here. So it's right here? 
**Andrew Magown** *[22:23]*: Yeah, it might be a pop up. It doesn't show originally, but move your mouse to the top of a mute button. It should show with a green pen. 
**Andrew Magown** *[22:35]*: No. 
**IAIN MAGOWN** *[22:39]*: Do I need to give him permission for that? 
**Dmytro Zotkin** *[22:42]*: No, I see. Disconnect audio, connect to devices, notes, whiteboards, background effects, meeting settings, captions, apps, record and chat. That's all. 
**IAIN MAGOWN** *[22:55]*: Might be whiteboard effects. 
**Andrew Magown** *[22:57]*: No, it's not. 
**Dmytro Zotkin** *[22:57]*: No whiteboard. It's separate whiteboard. 
**Andrew Magown** *[22:59]*: It's a whiteboard. That's a bummer. 
**WAHEED QAMAR** *[23:02]*: I just sent you a screenshot in Microsoft team so that you can. 
**Andrew Magown** *[23:06]*: Yeah, take a look at that on the side. Okay, so Ian, can you exit out of that tab? Release notes 1.8.7. 
**IAIN MAGOWN** *[23:17]*: You want to charge, right? 
**Andrew Magown** *[23:19]*: Just the release notes. 
**IAIN MAGOWN** *[23:20]*: Yeah, sorry, I need to move the. 
**Andrew Magown** *[23:24]*: Oh yeah, no, love zoom when it does that. 
**IAIN MAGOWN** *[23:27]*: All right, so I'm over here. 
**Dmytro Zotkin** *[23:29]*: No, visual studio code has its own. 
**Andrew Magown** *[23:33]*: Tab at the very top left. 
**Andrew Magown** *[23:34]*: There on the very top left a. 
**Andrew Magown** *[23:36]*: Little to the right, like go right. See where it says like it has now it says charge one XMl, charge four XML. 
**IAIN MAGOWN** *[23:46]*: Yeah. These guys. 
**Andrew Magown** *[23:46]*: Yeah, just exit out of the release note one. 
**Andrew Magown** *[23:49]*: Okay. Yeah. 
**Andrew Magown** *[23:52]*: And then if you could on the far left on that taskbar, the one with the squares on the bottom, click on that and I know what he'd already put it in, but I just want to double check myself. 
**Andrew Magown** *[24:06]*: Okay. 
**Andrew Magown** *[24:07]*: Yeah, so we have the XML red hat and XML tools. 
**Andrew Magown** *[24:09]*: Awesome. Okay. 
**Dmytro Zotkin** *[24:11]*: We definitely have to have it because I opened these files before the mission. That's exactly the files that we will be looking at. The thing is that we need to open Xslt as well. So we need to configure somewhere here that it actually gets the type for windows as a default type. So maybe we might need to go. 
**Andrew Magown** *[24:44]*: Back into the ide then to set the preferences on what files are opened when. 
**Dmytro Zotkin** *[24:50]*: No, ide doesn't have it. IDe uses system editor. It uses windows setting for the extension. 
**Andrew Magown** *[24:57]*: Then that's probably going to be in. 
**Dmytro Zotkin** *[24:58]*: The, we need to go in the windows. 
**Andrew Magown** *[25:01]*: Yeah. Okay. All right. 
**Dmytro Zotkin** *[25:06]*: We'Ll go to things. So let's just minimize that vs. Code. Visual studio code. 
**Andrew Magown** *[25:16]*: Minimize it, minimize id. 
**Dmytro Zotkin** *[25:21]*: Oh, we can just go into, and where do we do that? Maybe in the explorer. 
**Andrew Magown** *[25:28]*: Yeah, in the windows explorer, very bottom. No, not here. And go to the search option. 
**Dmytro Zotkin** *[25:38]*: And. 
**Andrew Magown** *[25:38]*: Then type in preferences. 
**Andrew Magown** *[25:43]*: This is windows ten. 
**Dmytro Zotkin** *[25:47]*: No, I don't think it's preferences. It's in windows. I did it last time. I don't remember, but let me just recall where it is. 
**Andrew Magown** *[26:02]*: Permissions to it. 
**Dmytro Zotkin** *[26:23]*: Start menu. Okay, default apps search for default apps. In the default apps search and type default. Default apps settings. 
**Andrew Magown** *[26:45]*: Yeah, that's where it is. 
**Dmytro Zotkin** *[26:48]*: And then where is. Okay, scroll down. Choose default apps by file type. Okay, very good. 
**Andrew Magown** *[27:08]*: Now. 
**Dmytro Zotkin** *[27:11]*: Okay, so on the left side find XMl. Just go to x so it will be at the very bottom. All like XMl xslt at least xslt XMl all this and WC. 
**IAIN MAGOWN** *[27:35]*: No, dang it. 
**Dmytro Zotkin** *[27:37]*: No, on the left side. The types are on the left side. Okay. 
**IAIN MAGOWN** *[27:41]*: Yeah, I clicked it by accident. 
**Andrew Magown** *[27:45]*: Yeah. 
**Dmytro Zotkin** *[27:46]*: Okay, click on XML. No, on the left side. Does it allow you to know? 
**IAIN MAGOWN** *[27:51]*: No. 
**Andrew Magown** *[27:53]*: Why? It's not allowing you to one. 
**IAIN MAGOWN** *[27:57]*: I can only click in the file over here. 
**Dmytro Zotkin** *[28:01]*: Okay, so let's find visual vs. Code. Yeah. 
**Andrew Magown** *[28:11]*: Choose enough and end. 
**IAIN MAGOWN** *[28:19]*: Close out. 
**Dmytro Zotkin** *[28:20]*: No, how does it know that? You have to choose on the left and then choose on the right. Right, like that thing. 
**IAIN MAGOWN** *[28:29]*: No, it just has me click on it and then it says, choose the app, which I just did, and then it all flickers. 
**Dmytro Zotkin** *[28:38]*: It didn't do anything. 
**IAIN MAGOWN** *[28:40]*: I don't see anything different. 
**Dmytro Zotkin** *[28:43]*: Choose an app window. Select an app you want to use default for that type. Find the file type that you want to associate with it and then click on it. So you have to click on the file type first. 
**IAIN MAGOWN** *[28:53]*: Right, I did. I clicked it here and then. 
**Dmytro Zotkin** *[28:58]*: Now do the same thing with xsl key on the left side. Xsl key. 
**IAIN MAGOWN** *[29:08]*: Sorry, again, I can't click on anything. 
**Andrew Magown** *[29:16]*: Why? It's not allowing you to click. That's funny. 
**Dmytro Zotkin** *[29:23]*: Can you scroll up to the very top? 
**IAIN MAGOWN** *[29:30]*: It's just the name. And then here's the. 
**Dmytro Zotkin** *[29:36]*: Okay, here maybe. And the gear, like, is gear doing anything? 
**IAIN MAGOWN** *[29:42]*: No. Back to settings. 
**Dmytro Zotkin** *[29:45]*: Okay, so how do we do that? White doesn't allow you to, oh, can. 
**Andrew Magown** *[29:52]*: You try clicking one of the plus buttons there? Choose a default possibly. And then click on the plus. That just says it's apps. 
**Dmytro Zotkin** *[30:05]*: See, it's applications. We need to associate the type. So we need to, like, according to Microsoft community, it says find the file type that you want to associate with a different app and then click on it. 
**IAIN MAGOWN** *[30:21]*: Choose default app by file type. 
**Dmytro Zotkin** *[30:24]*: So you need to find, scroll down to by file type selection. That's what we did, right. Can you close this one? Close this window and let's go again. Maybe we just chosen different default apps. 
**Andrew Magown** *[30:50]*: Default tabs. 
**Dmytro Zotkin** *[30:55]*: Yeah, and then scroll it down. Choose default by file type by protocol. And what else? 
**IAIN MAGOWN** *[31:09]*: Oh shoot, sorry. 
**Dmytro Zotkin** *[31:14]*: Do I try set defaults by app. The last one. 
**Andrew Magown** *[31:28]*: Okay, what does it do here? 
**Dmytro Zotkin** *[31:32]*: It doesn't even have vs code here. No, that doesn't work. That's the wrong place for by protocol. 
**Andrew Magown** *[31:42]*: It happens. No, that's not, can you select protocol? 
**IAIN MAGOWN** *[31:48]*: Oh, sorry. 
**Andrew Magown** *[31:52]*: Again. 
**IAIN MAGOWN** *[31:52]*: I can do it by the default app, not by the name. 
**Dmytro Zotkin** *[31:58]*: Okay, let's go by file type and try to drag and drop. Maybe it will drag and drop. Yeah, and then let's scroll down to xml. 
**IAIN MAGOWN** *[32:08]*: Hang on, it's loading. Come on. 
**Dmytro Zotkin** *[32:17]*: The important thing to get xslt. So let's go to xslt. Can you try to, okay, go to xslt line. Not vs. Code to xslt line. See xslt. Xslt. Xslt. Yeah, that's the one. Click at the application, then click. Choose an app. No, click on the application. And then on the top of it, can you click on. Choose. No. Up here and then higher a little bit. No, it's not. What about right click on that? 
**IAIN MAGOWN** *[33:19]*: No, of course you're not okay to meet drew. 
**Andrew Magown** *[33:24]*: May I try? I just want to check something. Ian, can you go back? 
**Andrew Magown** *[33:32]*: Yeah. 
**Andrew Magown** *[33:36]*: Click on default apps again. 
**Dmytro Zotkin** *[33:40]*: Oh yeah. Maybe try to find vs code at the bottom. 
**Andrew Magown** *[33:44]*: No, just go back to click on default apps in the tab or. Yeah, actually just go to the back to the main page. 
**IAIN MAGOWN** *[33:51]*: Default app settings which were just in. 
**Andrew Magown** *[33:56]*: Yeah. Okay. Then I would like you to. 
**Andrew Magown** *[34:06]*: Yeah, it's something. 
**Andrew Magown** *[34:40]*: It click on set defaults by app. 
**Andrew Magown** *[35:01]*: No, not that. 
**Andrew Magown** *[35:03]*: Click out of this. Sorry. 
**Dmytro Zotkin** *[35:07]*: We might not have a permission here. We need to talk to truck or it's done just through the application because for example I'm trying to do this on my laptop the same thing and I cannot even open that list for some reason. 
**Andrew Magown** *[35:25]*: Maybe I have too many I line. 
**Andrew Magown** *[35:46]*: Yeah, okay, sorry. Click on set defaults by app again. I just want to double check something. 
**IAIN MAGOWN** *[35:52]*: I feel like this is the closest thing that we're going to get. 
**Andrew Magown** *[35:54]*: This is going to be the closest thing. 
**Dmytro Zotkin** *[36:00]*: I don't know why it's not working. 
**IAIN MAGOWN** *[36:02]*: Okay, what should it look like once I hit it I should be able to click. 
**Dmytro Zotkin** *[36:09]*: Yeah, that's interesting. It shows for example I have XMl spy. 
**Andrew Magown** *[36:18]*: Right. 
**Dmytro Zotkin** *[36:20]*: XMl for example, I want to change the XML style. 
**Andrew Magown** *[36:29]*: Oh. 
**Dmytro Zotkin** *[36:35]*: Okay. So what's happening on my side? It's a pop up. Go to choose default app by file type and then go to xslt after it finishes. Go to xslt. 
**Andrew Magown** *[37:02]*: Scroll down. Yeah, xslt. 
**IAIN MAGOWN** *[37:08]*: Sorry. 
**Andrew Magown** *[37:14]*: Close. 
**Dmytro Zotkin** *[37:15]*: Now on the right side click. No, sorry. 
**IAIN MAGOWN** *[37:27]*: I know, sorry. 
**Dmytro Zotkin** *[37:29]*: Okay just click on it. Click yeah, here. Oh, now I know what's going on because for some reason it's not allowing us because for example for me I have choose an app, I have list of application and then look for an app in the Microsoft store. So I can put like any application here including vs code for here it's just one application. So it means that application has to allow to be in that list. So that's strange. 
**WAHEED QAMAR** *[38:08]*: Can we uninstall this app xml spy and then check? 
**Dmytro Zotkin** *[38:12]*: Maybe we can in this case it will give oh, can we remove it completely? 
**Andrew Magown** *[38:18]*: Yeah, we should be able to uninstall it. 
**Dmytro Zotkin** *[38:22]*: We definitely can uninstall and nothing bad will happen. So let's uninstall XMl spy. But I think how to. 
**Andrew Magown** *[38:31]*: Yeah, just go ahead and click on link. 
**IAIN MAGOWN** *[38:34]*: You want to remove this? 
**Andrew Magown** *[38:38]*: Yeah, I'm going to say uninstall mitro. 
**IAIN MAGOWN** *[38:41]*: Yes. 
**Dmytro Zotkin** *[38:43]*: Uninstall XMl file. You have to do it easier. That's much easier. Can be done without we need to uninstall it anyways because we don't need that spy in there because we don't have a preferences yet for it. Okay, but we'll do it differently while it's uninstalling. Let's go to any Windows explorer like I said from the very beginning. Yeah, Windows Explorer. No, just Windows Explorer. 
**Andrew Magown** *[40:15]*: Yeah, that's the one. 
**Dmytro Zotkin** *[40:17]*: Okay, just go to local drive c. Local disk C iD. Go to for example all projects one. 
**IAIN MAGOWN** *[40:40]*: Sorry, one. 
**Dmytro Zotkin** *[40:41]*: Sorry, all projects one. And then go to just open for example any project. So does it matter? Xslt folder all these files are accessible files. Just right click at any file properties. And then in properties where do we open? Change? Right here. 
**Andrew Magown** *[41:20]*: Trying up. 
**Andrew Magown** *[41:21]*: Yeah. 
**Dmytro Zotkin** *[41:21]*: Okay, now we need to hold on. Okay, perfect. And then we'll find excel file and that will be okay, perfect. Now close this one. 
**Andrew Magown** *[41:34]*: Close this one. 
**IAIN MAGOWN** *[41:36]*: Do you want me to hit apply first? 
**Andrew Magown** *[41:37]*: Yes. 
**Dmytro Zotkin** *[41:38]*: No, it's already applied. 
**Andrew Magown** *[41:41]*: He needs to hit apply. 
**Dmytro Zotkin** *[41:42]*: Needs to check the, yeah, just click ok everything or apply to all of them. I think just let's check. Try to double click at any of these files. 
**Andrew Magown** *[41:58]*: Yes. 
**Dmytro Zotkin** *[42:01]*: Okay, cancel this. We need to do one more thing. 
**WAHEED QAMAR** *[42:04]*: No. 
**Andrew Magown** *[42:09]*: The same prompt. So just. 
**Dmytro Zotkin** *[42:14]*: Close this for now. And then let's go to documents at the left side. 
**Andrew Magown** *[42:23]*: Documents TTT, my favorite CC protocol. 
**Dmytro Zotkin** *[42:32]*: Stripe. 
**Andrew Magown** *[42:36]*: Right click at any file. 
**Dmytro Zotkin** *[42:43]*: No. 
**Andrew Magown** *[42:45]*: Right click. 
**Dmytro Zotkin** *[42:46]*: Not left click. 
**Andrew Magown** *[42:48]*: Right click at any file. 
**IAIN MAGOWN** *[42:50]*: Hang on. 
**Andrew Magown** *[42:52]*: Right click. 
**Dmytro Zotkin** *[42:53]*: Okay, properties change. Okay, I think we roll set. Now let's go to id and let's try to open, I don't know if probably we need to restart id just to make sure that it picks up the system settings. So just open id, close it and then restart it. 
**Andrew Magown** *[43:28]*: Hang on. Okay, I'll close that for a minute. Close that. Yeah, we don't need all this. 
**Dmytro Zotkin** *[43:46]*: Usually when you're installing something, it automatically brings all extensions to the app. So you never do this. 
**Andrew Magown** *[43:56]*: It's working. 
**Dmytro Zotkin** *[44:01]*: Now close this one. Close this. We don't need that setting. 
**IAIN MAGOWN** *[44:04]*: You don't want to do this again? 
**Dmytro Zotkin** *[44:06]*: No, you're already done. Okay, so now let's go to that project. 
**IAIN MAGOWN** *[44:15]*: Which one were we at? It was stripe. 
**Dmytro Zotkin** *[44:19]*: SF toauthorized. 
**Andrew Magown** *[44:21]*: Net. 
**Dmytro Zotkin** *[44:22]*: It's alphabetical so you easy to find. Sf twoauthorized. 
**Andrew Magown** *[44:29]*: Net. That's the one expanded. 
**Dmytro Zotkin** *[44:33]*: Just click on plus. 
**IAIN MAGOWN** *[44:36]*: I did. 
**Andrew Magown** *[44:37]*: Okay, click on plus for integration flows. No. 
**Dmytro Zotkin** *[44:46]*: Click on plus on integration flows. 
**Andrew Magown** *[44:48]*: Integration flows. 
**Dmytro Zotkin** *[44:49]*: The last item. 
**Andrew Magown** *[44:50]*: I'm sorry. Okay. 
**Dmytro Zotkin** *[44:53]*: And then queries. Plus on queries. 
**Andrew Magown** *[44:57]*: Double click at first query very first on top. 
**Dmytro Zotkin** *[45:01]*: Okay, double click at the last transaction and then double click at data map and then edit icon against the pretransformer. And then the edit button. 
**Andrew Magown** *[45:18]*: Yeah. 
**Dmytro Zotkin** *[45:24]*: Okay, very good. So unfortunately, I don't know why, but it's not, oh, now it shows. Perfect. Okay, so that's XSLT. Now what else I want you to do is to open one more file or folder. Let's open any folder at the left side. There is. You can open folder on top of it. 
**Andrew Magown** *[45:58]*: Yeah. Okay. 
**Dmytro Zotkin** *[46:02]*: And open folder in the documents. 
**Andrew Magown** *[46:09]*: In the documents. 
**Dmytro Zotkin** *[46:11]*: TTT TC protest in stripe. No, go back and choose stripe. And click search. Select folder. Okay, so we have a file which is charge. Okay, so close charge. Four. Do we have like two? Oh, we have two apps. How do we make it with one app? Or it's just overwritten. Where is the transformer? Because Transformer was there. Where is it? It closed it. If you open folder, it doesn't allow you to open file. Okay, keep it like that. Let's go to id and then open again. Go to id. It's at the bottom. 
**IAIN MAGOWN** *[47:23]*: All right. 
**Dmytro Zotkin** *[47:26]*: Yeah. And click again on the editor. Hopefully it opens two of them right now. 
**IAIN MAGOWN** *[47:36]*: Hit open. 
**Andrew Magown** *[47:38]*: Yes. 
**Dmytro Zotkin** *[47:42]*: Perfect. That's what we need. So that's how transformer looks like. And at the left side, like in the left or top to the left of it. Charge, whatever. Yeah. So if you click on that one, that's the file that we are transforming. So now let's return back to the transformer itself and see what it actually has. The top of the transformer shows that which version of XML it will be transforming file to. So then actually which version of XML it will be transformed. Then it starts with a style sheet. Every transformer has style sheet. Style sheet determines version of XSOT, it's version 10. And it also determines this from year 99 very old transform template or wisdom which is used to do a transformation that's standard. It's always like that. The only thing that is changing here is the next line. 
**Dmytro Zotkin** *[49:04]*: Line number three, it's output. Output shows which document you're planning to create here. For XsLP version one, there was no JSoN output. So the output is text or JSon. If it was XML document or soap document or any other web services document, which is, which have an XML format. So it would be output xml. So output. In most cases output is XML or text. If for example, you're trying to create SQL queries, transforming your commands to SQL queries like we do for all Salesforce to clickbooks integration, it also texts. So it shows like which format of the output document is used. It's line number three. Line number four starts with the template. Template says that we will be transforming documents with the root element. Root XML element which is called IW transformation server. If you go to the document. 
**Dmytro Zotkin** *[50:20]*: Please share zero one XML go to the document. See it's line number two. See that's the root of the document that we are transforming. So template always has to correspond to a root of the document. Unfortunately it doesn't have an ability to close this. It doesn't have the ability. Why it doesn't. Guys, you need to look at the, so actually you need to be able to do the same like id for example has or any other XML editor to expand and compress this route. So it will be easier to. Now we will be just scrolling, but expand and close the roots or root elements needs to be there. Probably one of your plugins supposed to do that. Maybe it has to be just applied or activated. I don't know like how to make sure that these documents open using plugin. Not the standard territory. 
**Dmytro Zotkin** *[51:36]*: You probably need to do some settings in there. I will take a look at it after the call, but we'll be using like that. 
**Andrew Magown** *[51:44]*: But Ian, can you try on, just make sure you're selected on the IW transformation. That's the one you want, right Demetrio? 
**Andrew Magown** *[51:52]*: Yes. 
**Andrew Magown** *[51:53]*: Yeah, click on that. 
**Dmytro Zotkin** *[51:54]*: Can we collapse it? 
**Andrew Magown** *[51:57]*: I'm trying to remember, Ian, click control shift and then left bracket. Yeah, that'll root element and then basically to unfold it. 
**Dmytro Zotkin** *[52:15]*: Control shift right bracket, control, shift right. Okay. Usually it's not like the code, not the keyboard combinations, it's usually graphics. 
**Andrew Magown** *[52:28]*: But yeah, there is a method in visual studio code. I just remember off the top of my head because I don't know if XML, it is supported with it, but I'll look into that myself too. 
**Dmytro Zotkin** *[52:37]*: Yeah, okay, very good. So it will be more convenient. It can be done without it, but when you're looking at very long documents, collapsing is important. So now let's go back to the transformer. Go back to the transformer. 
**Andrew Magown** *[52:58]*: No. 
**IAIN MAGOWN** *[52:59]*: Oh, sorry, I keep frame. 
**Dmytro Zotkin** *[53:03]*: Okay, good. So now we started creating variables. Like variables, static variables are created before the actual transformation happens. The variable in XSLT allows you to grab any information from any element of XML document using Xpath expressions. But the very first one is kind of our own extension. Because when you look at the docu, at the variable like this, it means that the result of that variable will be just question mark because it's just a text between variable open tag and closed tag. It has a name and question mark means that it will be a first parameter of the flow and first parameter of the flow or first parameter of the transaction. And that's the test mode. So basically under question mark, when id is running over that transformer, it will bring the test mode. 
**Dmytro Zotkin** *[54:26]*: So it will be either in test mode or not in test mode. So this is kind of test request? Yes, no, it's a very simple one, but the next one has transaction source name. Transaction source name. The next, it's line number six variable transactions for the source name is supposed to grab the input parameter of the record that we are planning to pay from. That's usually id of the record or name of the record. It's called source name, but it can be used as id or as a source or as a name like depending on how we are doing a filter. Usually in most cases it's Id, but it could be a name as well. So if we switch back to the document, to the XML document. 
**IAIN MAGOWN** *[55:32]*: To charge. 
**Andrew Magown** *[55:34]*: Yes. 
**Dmytro Zotkin** *[55:37]*: And look at the param, we have to search. 
**Andrew Magown** *[55:44]*: Probably it's easier. 
**Dmytro Zotkin** *[55:48]*: Where is this name? Which line is it? 
**Andrew Magown** *[55:53]*: Let me find it here. 
**Dmytro Zotkin** *[55:55]*: Yeah, it's line number 18. In the line number 18 in this document we see the id. That id is from Salesforce because these are from Salesforce, that's not from, but of course records in creatio also have their own alphanumeric ids. So it will be id of the record. So this is how we create a variable which will be used as a record id in particular transformer because we are planning to send that record id as a part of the payment transaction. So basically when you look at the stripe, you can see in the special field of the stripe. Stripe has a special field for it. Okay, so that particular payment was done from the Salesforce record or create your record with this idea and you can find it backward like in corresponding CRM system. So this is how you create static variables. 
**Dmytro Zotkin** *[57:00]*: See, this is static variable. The first transaction session vars, it's always first part of every document that is generated by transformation server. So all these documents are generated by transformation server which is actually built using id and making this first transaction, it always session variables, it's a static part of the transaction. It doesn't contain any customer data unless like input data of the flow. But if you look at the, it has transaction name, it has server port, it has protocol, it has session ids, it has many interesting variables including flow name like counter and any other things that are usually important and usually kind. 
**Andrew Magown** *[58:05]*: Of. 
**Dmytro Zotkin** *[58:09]*: Becoming a part of these static variables. If you need that information to be used in the flow. For example, parameter of the starting of the ID record is required. So it's here in this partition. Let's go back to the transformer. So we created then current flow id also is coming from the, see when you look at the line number, for example, eleven, right eleven, it has current flow agent. It also taken it from the session VaRs transaction. If you look at the path for the line 13 in the test element of when it has path expression. So this is what you need to understand. That expression actually determines the place in the document from which information will be taken. And it goes by the root and branches, and then branches under branches like parent child elements. 
**Dmytro Zotkin** *[59:27]*: It finds, for example, transaction session vars, then data map, session vars, then data row. Go back to the document. Go back to the document. See at the structure. See transaction name session Vars, line number four, data map, line number five, data element line number six, row element line number seven, and then column element, line number eight. Line number eight. So these are the children of the parent element. Transaction, it's data map, data row and column. Now let's return back to the transformer. So this is exactly like in line 13. The xpass expression is created. Its record set is a root element, then transaction. Then under transaction data map and you specifying what are the names of those elements, like at name means that it's the attribute, because every XML element has attributes and values. Values could be another child element or just a text. 
**Dmytro Zotkin** *[01:00:49]*: So if you are referring to the attribute, you have to put at in front of the attribute name. If you go back to the document and look at the, for example, data map number five. So it has attribute session work. If you scroll down, or actually don't go down, just sit line number four. Line number four. Collapse it. I think you can collapse it here. Yeah, see, it has line number 74. Next transaction. And that data map already has a name login, right? Transaction has a name login to Salesforce and line 75. Transaction data map has a name login. Numb line number four. Okay, and this one has name session vars and name session vars like transaction and data map has the same name here. 
**Dmytro Zotkin** *[01:02:04]*: So if you go back to the transformer, you see that in the line number 13 we are taking, the engine tries to find transaction which has a name attribute equals to session bar. That's how it knows where to take the information. 
**Andrew Magown** *[01:02:24]*: From. 
**Dmytro Zotkin** *[01:02:26]*: And it gets like name current flow id. It's in the column current flow id. If you go back, you can see that column current flow id go back to the document. Yeah. So if you go here, see the whole bunch of columns starting from the row number eight. And it goes column, column. Each column has its own name. So current flow id will be somewhere down there. It should be close to the top. 
**Andrew Magown** *[01:03:10]*: Okay, so I don't know why it's not. 
**Dmytro Zotkin** *[01:03:12]*: Oh, it's not there, by the way. And I will show you why it's not there. Because current flow id is only for flows. We are going for query. In order to create a proper logic, let's go to the transformer for that variable. It has the element when so choose element line number twelve. It's like xslT command which says that we are choosing one of the values. Line 13 says that when that current flow id exists. Because if it's not equal to anything, it means that it's just checking whether this exists or not. It takes the value of. See, it says line number 1214, it's value off. And then it shows the same expat. Otherwise it's underline number 16, it gets a text. So we are just setting up that this will be this particular transaction which I don't know what is this default value? 
**Dmytro Zotkin** *[01:04:23]*: It's never used. It's from some other transformers. Because transformers are cloned, copy it. And some elements don't belong given here. But it's for like for the exercise. As an exercise, it's clear. So you see in the document, we don't have that element. If we don't have elements. So we will be doing like default name of that particular flow. That's a logic. It will be very useful for you to go through all these elements and find them in any xslt for dummies or like xslt documentation and figure out what they are. Just to remember all of that next element. For example, it's API type. And that's for the type of API important thing that it has if statement. See line number 20. Line number 20. It has if statement. 
**Dmytro Zotkin** *[01:05:30]*: If statement means that if that parameter, again in that session variable exists, then the value of that parameter will be API type. Otherwise API type will be empty. Now we don't know what to do with empty API type, but if it contains information, the next variable, if you look at this, it checks whether API type contains column. Because there could be like API type and something else. It's special way of representing which object. I believe will be used there. So I don't even remember what it was. Where is the API type used again? Oh, that's, yeah, so it depends whether post API that's specifically for. It's actually elements. Not even for stripe or. No, it is for stripe. Yes, it is for stripe, because stripe has two levels of APIs and one of them is created here. 
**Dmytro Zotkin** *[01:06:46]*: It's chosen field, so information can come with the special formats. Usually delimiter is a column, and if you look at the line number 27, for example, you will see that select statement contains a function. XSLt has a lot of function select substring before. It's a substring before of API type, it's variable which was defined in the line number 19. So that is the variable. So it says if in line 26 it says if it contains colon, then the value will be substring before colon, and information after colon is used somewhere else. Important thing that you know all these functions. Substring before, substring after contains all these functions. It's a standard SSL key one functions. There are like 20 or 30 of 25 of them, not many. If you don't remember what this function is about, you can always google it. 
**Dmytro Zotkin** *[01:07:57]*: It immediately jumps up and explains what. 
**Andrew Magown** *[01:08:00]*: That function is doing. 
**Dmytro Zotkin** *[01:08:02]*: So the structure of all those variables is the same for all these things that are going. Let's scroll down a little bit. 
**Andrew Magown** *[01:08:18]*: Mmhmm. 
**Dmytro Zotkin** *[01:08:21]*: For example, if we look at the 58 56, at line 56 processing mode. So see it has like same choose element, but it has three options. It's like case variable in any programming language. It has like when for example current flow id equals to this. So it will be from stoppage, it's some processing mode. And then it could be normal. If processing mode zero equals empty normal. And otherwise it's processing mode which is determined in session vars transactions. So see like there are three options here. You can have many options here, as many as you wish. There are no limitations. Important thing that you know that choose element allows you to do like selection of multiple conditions. If element has selection only of one condition, there is no if else structure. 
**Dmytro Zotkin** *[01:09:31]*: It only like in order to do if else you need to do choose when. Otherwise it's kind of just one when and then otherwise it's. In other programming languages it's if else statement in this particular one shows command makes. It corresponds to that logic. Then let's scroll down to line 65 and then try to collapse line 65. So it actually does something. If processing mode equals set storage. So if you expand it now say from line 65 to line one, you scroll down to 130. Down, down. 
**Andrew Magown** *[01:10:39]*: Right? Yeah. 
**Dmytro Zotkin** *[01:10:43]*: So that's where it finishes with line 65. It finishes. By the way, if you look at the line 129, 130, and 131, they are commented out. That's how you're commenting. That's the same like you're commenting in XML document Xslt, the same thing that you're doing. It's how you comment part of the transformer. So basically, until line number one five two, we're determining static variables. We are not processing any data yet. We are not doing any output yet. Like, we are not producing any output yet. Producing output starts from the line number one five two. It's for each statement. Actually, no, that's not true. It is true, but the structure of this is a little bit more complicated because we are allowing to build multiple accounts in stripe. So you can pay to multiple accounts in stripe. 
**Dmytro Zotkin** *[01:12:16]*: And based on some conditions that are in the records from which we are paying, like if that value in the record equals to this, so then this account will be paid and so on and so forth. So this loop is actually, it's a loop of all accounts. So it will be determining which account it is. And if account is not correct, one, nothing will happen like I believe. Line number try to collapse. One five two. 
**Andrew Magown** *[01:12:57]*: Okay, no, it's not collapsing correctly for. 
**Dmytro Zotkin** *[01:13:04]*: Some reason, because it doesn't understand text documents inside. Not good. 
**Andrew Magown** *[01:13:11]*: Okay. 
**Dmytro Zotkin** *[01:13:12]*: Anyway, so that for each goes till the very end of the document, it's external loop for all possible accounts in stripe. Let's scroll it down a little bit. There is some logic in here. I just want to show you the entire structure and then we'll talk about the other elements. Okay, so let's go down here. Line number. 
**Andrew Magown** *[01:13:42]*: 170. 
**Dmytro Zotkin** *[01:13:43]*: So line number 170 is actual processing of the data. See, it is trying to create a loop for each. It's a loop. It is processing all elements that are determined by xpass expression inside the select statement for each. The loop in XSLT has a very limited abilities to be used. It's just the loop on the document. Say if in the document you have multiple, if you go for example here, see at the end of the line number 170. At the end, see, it is finished with row set query response, right? No, it's roset. Go to the row set records. Let's go to the document and let's scroll down. Or actually just collapse. Now you know how to collapse the collapse. Transaction transaction session vars line number four. Then can you move your a little bit. Okay, very good. Collapse. 74. 
**Dmytro Zotkin** *[01:15:31]*: And see, this is where the real data appear. So if you look at the line number 117, that's role set name record for particular query. You always process in one record. If it's a flow, you can have multiple records, one after another. It will be record, then record. So the loop will be right. Now that loop contains just one element, one step to process this particular set of data. So that's how, if you look at the, on top of the line number 117, see, it has result query response, body, envelope, you can see all of them, right? So in this case, if you go back to the transformer, you can see all this element, 171, 71, 70, you can see envelope body query response to the right, and so on and so forth. 
**Dmytro Zotkin** *[01:16:52]*: So this is kind of the address of the element which is looping, what we are looping through. We are looping through records which are returned by the system that we are querying in the first part of the flow. So we are querying the, it could be more than one element returned, like for scheduled flow, for query, it's usually just one element, actually, always one element, with a very few exceptions. In any case, this particular transformer can work in the query and in the transaction flow because it does the same thing. It just creates a part of the document that processing one particular payment from one particular record. If you have like five records, it will create five payments. That's the loop. If you need more sophisticated loops, you need to use templates. And it's a little bit more complicated. 
**Dmytro Zotkin** *[01:17:56]*: I will talk about it during our next training session, how to create more complicated loops in XSOT. But at this case, we know that we have a loop that actually goes through every record. So now let's look at one or two examples, how we are getting the data out of the document and then how we are creating the output document. I'm just trying to go through all elements. So scroll it down a little bit more down. And see. So variable line number 20, four. That's the one. See variable name Salesforce Id. It has like row call name Id. Let's go back to the document. See, like after the records, you remember that we are looping through the row set records. It has row, and then in the row it has columns, and columns have different names. Type id twice, not for some reason. 
**Dmytro Zotkin** *[01:19:20]*: That's how Salesforce returns. Name, gross monthly charge, credit card client, all these kind of things. So these are the data which are coming from the incoming system. So how do we determine here that we want to actually use Salesforce ID for some reason. So looks, if you look at the line number 121 20, right, you see that it has a name id. Let's return back to the transformer. So we are putting a path like select that. What determines where we are taking this data? Row call. And then call from the call, it's element with the name Id. And the next line 25, it grabs the name of the record. So see this is how you are getting information or creating a variable which you will be using later. 
**Dmytro Zotkin** *[01:20:29]*: You're creating variable, not necessarily that you need to create these variables, but if these variables are used in several places, it's better to use a variable because it's a little bit, working a little bit faster and more convenient, because if you need to change that path, you're changing it just in one place, not everywhere in the program. So you need to understand this syntax of that expression. What is inside that select? It's an xpass expression which allows you from the root. When we are creating a loop, that loop determines what is the root element of the loop. Root element of the root. That last row set inside this row set could be rows and columns. Inside rows. So one row, several columns. This is the syntax for getting information from particular XML document. It's called xpass. 
**Dmytro Zotkin** *[01:21:37]*: Also if you want to use it seriously, you need to look into some documentation about xpath. Xpass. It's a base of xslt. XSlt is based on xpass. Like most of the functions and elements of xslt are using xpass expression. So this is xpass expression which very simple one, which allows you to get the particular element of the document, the particular child of the document. It's when we are getting column with a proper name. The document itself is generated by output of the previous transaction and that document is generated by transformation server. The document that you were looking, I already told you this several times, I'm just repeating it again. The document itself, it's our internal format. So all documents which are going between transactions will have that format. 
**Dmytro Zotkin** *[01:22:34]*: All our internal adapters are reading data and converting into that format from XML, from name value pairs, from JSON, from just clear text, from formatted text, from any other formats that you can imagine. Results of SQL query like everything will be converted internally into that document. So the document for all, every single project, all transactions are working with the same document structure. It's rows and columns. It's very generic approach and it actually works for all formats that appear way later than the format. Our internal format was developed, which means that it was developed properly because it allows us to cover all new formats that arrived after it. When we developed it, JSON, even such concept didn't exist, but it perfectly working well with JSON documents. It creates the same document from the JSON. 
**Dmytro Zotkin** *[01:23:52]*: So if you look at this variables now let's scroll down a little bit. That will be the last part. More down more, down, more, down. 
**Andrew Magown** *[01:24:11]*: More. 
**Dmytro Zotkin** *[01:24:12]*: These are all variables like more complicated, less complicated. See these variables about the address. Continue. Going down, continue. 
**Andrew Magown** *[01:24:27]*: I'll tell you when to stop. 
**Dmytro Zotkin** *[01:24:29]*: Stop here. Okay, so here, that's where we are starting to get the data out. 
**Andrew Magown** *[01:24:39]*: You. 
**Dmytro Zotkin** *[01:24:43]*: See it has line 179 or 379, sorry. Which says if API type equals WS is web services. So if we are using web services, that's how we are building the JSON document. And see what it is. It has like merchant provider id. And then look at the value at the line 381. It's the value of variable API login. It's API login that was variable that was determined at the very top of the transformer where were getting data from the session variables. And this is API login from our configuration, from the profile which is read by the transformation server before everything is started. And parts of the profile are always in the session bars. Transaction sessions includes entire configuration profile, always. So this is login and then transaction key. So these are credentials which are going in value off. It's a value of that. 
**Dmytro Zotkin** *[01:26:09]*: So it will be id, open, double quote. Then this will be the value of that variable and then it will be closed, double quote in the line 382 and so on and so forth. It will be like many variables. You see that it contains those curly braces which are the arrays in JSON. So this is how we create output document. It has text elements which are like static elements of the JSON. It's a format of the stripe web services request which we know which we are putting in there. And parts of this are the names of the variables. Like every JSON, it has a name and name could be id or name could be, for example. 
**Andrew Magown** *[01:27:05]*: Card. 
**Dmytro Zotkin** *[01:27:05]*: If you look at the line number 193. Yeah, so this is how it determines card. See, it's an array of multiple variables. And first variable Pam, why they call it like this. But this is credit card number, the CCC number variable CC number which was taken from the document. Document contains credit card number. Document a variable was created. If you go back to the document. 
**Andrew Magown** *[01:27:44]*: Go back to the document. 
**Dmytro Zotkin** *[01:27:47]*: You can see here that line credit card client. It's 124. It contains the name of the credit card. So this is how it goes. And when you go back to that particular transformer, go back to transformer. So we created that variable higher, that variable CC number. It's taken information from that particular child element of the XML document. And here, the value of that credit card number is included into the JSON document. The text that you can see here, the text, it's xslt element that allows you to create a text output message. If you don't use text, just put this like that. It will be the same thing if there is nothing around xsot transformer. It's considered as a text, but text allows you to format it better. 
**Dmytro Zotkin** *[01:28:58]*: Also, text elements allow to create a logic, because you cannot create a logic inside just clear text. You need some elements. If you're talking about some elements, you can use these text elements. In 90% cases, you might not need that, but like in 10% cases, they are useful. Again, as I said, if you use text or you don't use text, it's. Oh, I'm sorry, Bruce. Bruce, are you with us? Bruce? Yes. Meeting is starting. Okay, Rupert. 
**Andrew Magown** *[01:29:48]*: Yes. 
**IAIN MAGOWN** *[01:29:49]*: How's your nap? 
**Andrew Magown** *[01:29:50]*: Got it. 
**Dmytro Zotkin** *[01:29:52]*: Okay, so we need to restart the meeting. So this is first part. We'll continue with this transformer during the next meeting. Please prepare some questions, and please review the elements that we are reviewing or like using here. Please review it so you will understand better. Okay, thank you very much, everyone, and let's switch to another call. Have a nice day. Thank you. Demetrius, just close this. 
**WAHEED QAMAR** *[01:30:24]*: Thank you. Thank you. Okay. 
**IAIN MAGOWN** *[01:30:36]*: This is awkward. 
**WAHEED QAMAR** *[01:30:37]*: I think the best way is, like, to record using other screen recording software, and then we can schedule a meeting just for questions. So, in this way. 
**IAIN MAGOWN** *[01:30:50]*: Agreed. 
**Andrew Magown** *[01:30:51]*: Yeah, I would say right now, let's all review the recording, and then do. 
**IAIN MAGOWN** *[01:30:57]*: You think the dads are fighting? Yes, absolutely. 
**Andrew Magown** *[01:31:02]*: But, yeah. 
**Andrew Magown** *[01:31:04]*: Also, have any of you looked at the tutorials for XSLT and XML? 
**IAIN MAGOWN** *[01:31:14]*: No, not my area of expertise. 
**Andrew Magown** *[01:31:19]*: Okay, well, I would suggest just kind of like, taking a look at those, because in terms of just kind of general setup, it's not overly complicated. I mean, once you start getting into the. For each and all the other jazz, it starts getting a little tricky. But, yeah, I need to figure out what the heck's going on with that flowchart thing in the iDe. I don't know why I can't see it. So I got to take a look at that later. Let's make a separate Microsoft Teams chat between the three of us and just basically just notifies or we notify each other whenever we're on the ide. 
**WAHEED QAMAR** *[01:31:57]*: Yeah. Okay. I was having a very basic question. We have customer portal, and when object selection pages and all the things are filled out, and at the end it says, like, save and finish. So a solution is already created when somebody clicks on save. And so, like, why we need the ide to create the solution. 
**Andrew Magown** *[01:32:21]*: So it's basically just, it's Demetrius own program that he created. That's how he does all the integrations. Honestly. Let's see here. Who set up the recording? 
**Andrew Magown** *[01:32:36]*: The recording has stopped. Yeah. 
**Andrew Magown** *[01:32:40]*: Honestly, if we started researching it ourselves, we probably could find a separate program that doesn't require the interweave IDe to do this integration. But it's their own company properties, so that's probably why they're prioritizing using it overall. But the IDE is where all the connections happen, and that's where all the property or the intellectual property of interweave is. So that's probably why we have to do it through the ide itself. Yeah, I don't know. Otherwise, I was thinking about looking into setting up, like, a GitHub profile for the company and seeing if we like. That way we can all collab on the code together and not have to be stuck going into an Ide separately. I can talk to Dimitri about that later. 
**Andrew Magown** *[01:33:38]*: First thing, my top priority right now is finishing this cratio app, which is such a pain in my ass, but it's progressing, thankfully. 
**WAHEED QAMAR** *[01:33:51]*: I think you have studied the basic, and me and Ian are on the same page. Like, we are a little bit slower than. 
**Andrew Magown** *[01:34:01]*: Like, when I look at the code, I'm a little familiar with it, but there's still a lot of it that I don't know how to implement. I mean, if I read it, I'm like, okay, I see what's happening, but then if I'm asked to use it to make something, I'm going to be like, Dimitri, I don't even know where to start, dude. 
**IAIN MAGOWN** *[01:34:21]*: Would it be out of the realm of possibility that this is also a tab? Outdated? 
**Andrew Magown** *[01:34:25]*: Yeah, 100% outdated. Okay, but this is what we got. Let's just set up the integrations and stuff first, make sure things are working, and then down the road we can start trying to find new versions and new technologies that we can get ourselves a little ahead here too. 
**IAIN MAGOWN** *[01:34:47]*: Yeah. Bruce is all on implementing the AI chat bot for help desk, which he's charged me to. He, he definitely does want to get to easier quicker. He's trying to build out this company so that he can grow this thing so that it can actually be a well oiled machine and easier for other customers to use. So, yeah, if there's anything that you come across or anyone you know that has the expertise to really bring us forward, then by all means, please suggest it. 
**Andrew Magown** *[01:35:29]*: Okay, is he asking you for doing the chat bot on the help desk? 
**IAIN MAGOWN** *[01:35:36]*: Currently the idea is that it's going to be throughout all the domains, but right now it's being tested on the help interweave. Overall, the idea is to make it really easy for customers to ask the question inquiries and not having to have us on multiple tech support calls, because that seems to be where Bruce's frustration is, and Demetrius as well. They have to be on two, three to five calls every day for one to 2 hours at a time helping customers, which is where our strength is, but it's very time consuming, and hopefully we can get the onboarding process. 
**WAHEED QAMAR** *[01:36:34]*: So Bruce, okay, Bruce asked me to export the knowledge base article. So, like, how can we train that artificial intelligence model to feed them all the. 
**IAIN MAGOWN** *[01:36:46]*: So between the three of us right now, the company that we're looking at is they're also in the test phase of their own chat bot. So I don't have the answer for that one right now. All I know is that right now what it does is it just simply looks for anything on the website right now. But I think what Bruce is trying to have us do is put everything together into one section so the chat bot can just feed off of that. I don't know yet. So we're going to wait until we find out from their team as to when their chat bot is up and running. It looks like if we go with them, we're kind of going to be going parallel in terms of development. Does that make sense? 
**IAIN MAGOWN** *[01:37:40]*: I know that he doesn't want me to copy and paste everything over time. As far as, and then whatever database he has in mind, I don't really know. He's talked to you more about that, right? 
**Andrew Magown** *[01:37:52]*: Yes. 
**IAIN MAGOWN** *[01:37:57]*: I don't know that I'm doing what I can. What did he, oh, hang on. What else did he discuss with you? 
**WAHEED QAMAR** *[01:38:13]*: Yeah, so he said we cannot export the knowledge base articles if we can export it, so it will be in WordPress format. So I think the best way is to copy that to a word document, and if there is any option to feed that with that information, because some of the documentation has, like, they are password protected. So I don't think the chatbot can access that. We create a username and password for our customers to access a particular document, for example, Salesforce to quickbooks or something, so they cannot be accessed by. 
**IAIN MAGOWN** *[01:39:00]*: I assume at that point then, oh man, that's more involved than. So would it be in the same, could it be easier by the portal access? That's when they're able to access the chat. Is that a function that we can do or is that too much? I don't know if that makes sense. 
**WAHEED QAMAR** *[01:39:28]*: I mean, if the chatbot can provide us a dashboard and there is an option, like to add some links so we can make some private pages in the website, and in that way we can access that. 
**IAIN MAGOWN** *[01:39:41]*: Okay, Wahi. Going forward, if you have any suggestions, please feel free to talk to express your opinions, or if you have something that, you know, you feel better than our own expertise, please let us, yeah. 
**Andrew Magown** *[01:40:06]*: Okay. 
**WAHEED QAMAR** *[01:40:07]*: I will get back to you on that. Like if I found something. 
**IAIN MAGOWN** *[01:40:10]*: All right, cool. 
**Dmytro Zotkin** *[01:40:11]*: Anything else? 
**Andrew Magown** *[01:40:12]*: Yep. 
**Andrew Magown** *[01:40:14]*: Andrew, I think this is a pretty good conversation we're having. We should have more like. 
**IAIN MAGOWN** *[01:40:22]*: Dokie. All right, I guess we'll talk later. 
**Andrew Magown** *[01:40:26]*: Yeah. 
**WAHEED QAMAR** *[01:40:26]*: Thank you. Thanks for your time. 
**Andrew Magown** *[01:40:28]*: Bye. 
**WAHEED QAMAR** *[01:40:28]*: I will send you the recording. 
**Andrew Magown** *[01:40:29]*: Yep. Thank you. Thanks. 
**Andrew Magown** *[01:40:31]*: Talk to you later. 
