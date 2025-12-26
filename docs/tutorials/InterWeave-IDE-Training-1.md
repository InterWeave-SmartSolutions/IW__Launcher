# InterWeave IDE Training Zoom Meeting

**Meeting Date:** 16th Jan, 2024 - 12:00 PM

---

**Dmytro ZotkinDmytro Zotkin** *[00:10]*: Morning. 
**bmagown@interweavebiz** *[00:12]*: Good morning. How are you? 
**IAIN MAGOWN** *[00:15]*: Snowed in. 
**Dmytro Zotkin** *[00:18]*: Close the door. 
**IAIN MAGOWN** *[00:20]*: How's it down there? 
**WAHEED QAMAR** *[00:22]*: This meeting is being recorded. 
**IAIN MAGOWN** *[00:27]*: How are you, Waheed? 
**Dmytro ZotkinDmytro Zotkin** *[00:28]*: Ann, how are you? 
**bmagown@interweavebiz** *[00:38]*: All right. Looks like people are here in your spatial room there, Ian. 
**IAIN MAGOWN** *[00:48]*: What's that? 
**bmagown@interweavebiz** *[00:49]*: You have a spatial background? 
**IAIN MAGOWN** *[00:53]*: No, this is my interweave background. How I represent the company. I'm not going to pretend like I have more than I have. 
**bmagown@interweavebiz** *[01:11]*: All right, well, we have quorum. 
**Dmytro ZotkinDmytro Zotkin** *[01:16]*: Good afternoon, everyone. 
**IAIN MAGOWN** *[01:18]*: Afternoon. 
**WAHEED QAMAR** *[01:22]*: Okay, let me see who's here. Okay. 
**Dmytro ZotkinDmytro Zotkin** *[01:27]*: Hi yan. Hi wahid. And hi Bruce. That is me. Okay, so I'm not going to say hi to AI, but that's okay. Hopefully. 
**IAIN MAGOWN** *[01:49]*: You should probably start now. 
**Dmytro ZotkinDmytro Zotkin** *[01:54]*: Okay. Maybe the earlier you get connections, the better. Yeah. Okay, so today we'll start talking about Ide. I'll give you some introductory materials, explain a little bit what ide is, and point you out to the documentation that you can take a look at after this meeting. Who has the most stable connection to the test table having? That's very good. We are on the server where we need to be. So let's start. Id is the program that allows to build solutions that can create integration processes between different integration points. The id is built in Java. It's based on the Java ID that is eclipse and later version of different Java ID. They require to build your own ide. 
**Dmytro ZotkinDmytro Zotkin** *[03:32]*: So that was built out based on eclipse, but it's like the program that was fully built by us and that's what is like feeding us for last 15 years. So the schema or the architecture of IDE is not very complicated. It allows you to connect to integration points and then to transform the information which is read from one integration point and sent or written to another integration point. Internally it does a conversion, and conversion is built on the fact that internally IG support special format, that is our IW format, Iw schema. And that schema actually allows us to create solutions which are decoupled. So every solution, every information that we are reading from one integration point is converted to our internal format, internal XML format, and then from that internal XML format it's converted to the destination format and destination format. 
**Dmytro ZotkinDmytro Zotkin** *[05:08]*: Could be any possible remote or local API that are used. Like we are not using too much the very local API, but they are available. So it could be like remote calls to the functions, but mostly it's XML documents, in particular subdocuments and rest documents based on JSON protocol. So that conversion also includes, of course when mitten starts, Alex calls. That's the usual part. Alex, I'm in a call. 
**Dmytro Zotkin** *[05:55]*: That's ideal. 
**Dmytro ZotkinDmytro Zotkin** *[05:58]*: I'll call you back. Yes, so we. So it's not just converting information from one format to another. It also updating the information, extending and formatting information. So if additional data are required to be sent to the destination data point, the internal engine is doing that. Id builds two end results, one end result. It's integration manager. Integration manager. It's the program that actually controls integration flows. It manages configurations which are stored in the database and it also assigns those configurations to particular flow instances. It's a multithreaded product. We use them on Linux server. It can work under Windows or Unix or Linux because the result is war files. War files, it's web archive. It's a web application that can be deployed to any web server that supports war format like most of them do. And we're using Tomcat. 
**Dmytro Zotkin** *[07:26]*: But it can be actually we are. 
**Dmytro ZotkinDmytro Zotkin** *[07:29]*: Going to maybe within a year. 
**Dmytro Zotkin** *[07:33]*: We have actually to go a little bit upper. 
**Dmytro ZotkinDmytro Zotkin** *[07:35]*: But at this point it's Tomcat and web applications. There are two web applications. The one I already explained, it's integration manager. And another web application is a transformation server which actually is doing all these transformations that I described. Now let's look at the id itself. So let's open Explorer. 
**Dmytro ZotkinDmytro Zotkin** *[08:03]*: Okay. 
**Dmytro ZotkinDmytro Zotkin** *[08:07]*: No, not Internet Explorer. Windows Explorer. 
**WAHEED QAMAR** *[08:19]*: Okay. 
**Dmytro ZotkinDmytro Zotkin** *[08:20]*: And let's go to drive c. No, that's your desktop. Local drive local disk c. That's the one. 
**WAHEED QAMAR** *[08:37]*: Okay. 
**Dmytro ZotkinDmytro Zotkin** *[08:37]*: Yeah, and see this is IW underscore id. That's the folder which contains id. You need to go to subfolder which has the same name but with small letters versus capital. And let's do the following. Right click on the ide application. It's a second from the bottom. Right click on it. Right click on it. Click send to and make it desktop create shortcut. So the iD is built as a windows application. Now let's go to the desktop and that's where we will be starting ag from. Double click at it and let's wait until it starts because unfortunately that test server is not. 
**WAHEED QAMAR** *[09:51]*: What is this? 
**Dmytro ZotkinDmytro Zotkin** *[10:00]*: So test server is not very fast, so we need to wait until it opens. Bruce, who's Kevin Ryan or peer Genix? Bruce, while it's open. What is this median with pure genics? 
**bmagown@interweavebiz** *[10:20]*: About oh, n four. Small configuration, small solution. 
**WAHEED QAMAR** *[10:30]*: Yeah. Okay. 
**bmagown@interweavebiz** *[10:34]*: That'S a slow server. 
**Dmytro ZotkinDmytro Zotkin** *[10:37]*: Yes it is. No, that's like a heavy product. Okay, so here we go. So this is the id. So as you can see it has several screens and several areas in the screen. Navigator goes between the projects and these are the projects we copied all projects that we are having right now. It's not the latest versions, but it's close to the latest versions if you look at this. So the projects that are really important at the moment, it's the project SF to authorize. Net see like here, that's the project for all credit card solutions. Then SF to QB base and SF to QB custom. It's two projects for Salesforce to Quickbooks integration base project is for account receivable and custom is for account payable and accounting pieces which are not account receivable. 
**Dmytro Zotkin** *[11:47]*: And account payable but accounting pieces. 
**Dmytro ZotkinDmytro Zotkin** *[11:49]*: Also we have the project with a weird name, SS test. I believe that one includes solutions for creative. So if you open, let's go to for example authorize. Net acceptautorized. Net project and just open. Just expand that node here. 
**WAHEED QAMAR** *[12:15]*: Expand it. 
**Dmytro ZotkinDmytro Zotkin** *[12:17]*: Yeah, so it has configuration, transactions, connections, XSLT transformers. These are all parts of the integration flows. Integration flows please expand. Integration flows are the processes that are actually exchanging data between integration ports points. Two types of integration flows. It's transaction flows which are like scheduled back end solution, back end flows, and utility flows and queries. Queries are the flows of special type that are subject to pseudorest API that we support. So these flows can be called using URL from buttons, from creatio, from buttons from Salesforce, from button, from anywhere where you can create the button, or from any automation that allows you to go to external URL. Later we'll talk about those external URLs. But talking about that, if you open queries, for example, just expand the queries. You see the very top one is a creature to stripe. 
**Dmytro ZotkinDmytro Zotkin** *[13:46]*: That's the one that we will be playing with when we will be trying to connect creatio to stripe and to use it in the portal and so on and so forth. So these are the flows. Now, open transaction flows. Open transaction flows. Transaction flows are like flows which actually are running based on the schedule or as utility flows here are only scheduled flows. Utility flows are also here. But let's look at the simplest flow which is SF transactions to authorized. It's the one that sends information from Salesforce to authorize. 
**WAHEED QAMAR** *[14:37]*: Net. 
**Dmytro ZotkinDmytro Zotkin** *[14:37]*: Just double click at it and see the splitter between windows considering that screen is small. So just move it down a little bit. The splitter between up and upper and lower sections. Like in between upper and lower. A little bit down. Yeah. This splitter, yes, this splitter. Move it down a little bit. 
**Dmytro ZotkinDmytro Zotkin** *[15:12]*: Okay, can I provide you the remote access so that you can control my screen. 
**Dmytro ZotkinDmytro Zotkin** *[15:18]*: No, that's not a good idea. Just like console has to be closed. Just close console. Okay. And just move it again a little bit down. Okay, good. Now what I don't understand is why it's not showing the bottom of those. Can you scroll the scroller? Okay. Yeah. So you need to extend that one as well. You can extend this area, extend it a little bit. That's probably your resolution of your computer here. Just move it down a little bit. 
**Dmytro ZotkinDmytro Zotkin** *[16:08]*: Yeah, it's Blink. 
**Dmytro ZotkinDmytro Zotkin** *[16:13]*: Okay. No, so it's just the resolution of the computer. Just move it up and I will show you. What are these things? Just move it back. Okay, it's again like that server is not like the best thing to use, but so far we are using this one. Bruce, we may need to talk to trap to accelerate that server a little bit. 
**bmagown@interweavebiz** *[16:42]*: All right, that's fine. 
**Dmytro ZotkinDmytro Zotkin** *[16:45]*: Okay, very good. The second section from the top on the right side is the parameters of the flow itself. This flow is scheduled over the interval. You can open that drop down and see what are the options. See like id then? Yeah, here, just open that drop down. It could be specific time, daily specific time, day of the week, specific time and date. So it's kind of scheduled and single run configurable. Single run configurable. It's for utility flows. If you go up single run only, that's for flows which can never be scheduled. So these are the ways how you determine whether flows can be scheduled or not. Now solution is clear. It's the salesforce authorized net. Other parameters you will see in the documentation. I will not be introductory. What you can see on the top are like 123456 transactions. 
**Dmytro ZotkinDmytro Zotkin** *[18:02]*: So each block on the top is a transaction. So first transaction, SF login CM. It's a transaction that actually logins to Salesforce. So if you right click at the transaction, you can see that you can edit transaction and you can determine next transaction. So if you click at the next transaction, you have a list of transactions. You need to extend that a little bit, that screen. 
**WAHEED QAMAR** *[18:38]*: Okay. 
**Dmytro ZotkinDmytro Zotkin** *[18:39]*: For some reason it's not something wrong with the resolution here. Okay, so how do we do that? What would be like the best way? Okay, click cancel here. It's enough to things to explain, but I need to look because when I was opening on my desktop from the server, I didn't see any such behavior. So probably that's a resolution of your screen. It has to be put down a little bit. 
**bmagown@interweavebiz** *[19:11]*: Can you expand the entire screen here? I mean top right. Right there. 
**Dmytro Zotkin** *[19:15]*: Would that help? 
**Dmytro ZotkinDmytro Zotkin** *[19:16]*: Yeah. Okay. 
**Dmytro ZotkinDmytro Zotkin** *[19:18]*: No, but that's not. No, that's not it. 
**bmagown@interweavebiz** *[19:21]*: All right. 
**Dmytro ZotkinDmytro Zotkin** *[19:21]*: No, that's a resolution. That's a resolution of the screen in pixels. It has to be changed. Okay, so that splitter, the lowest splitter that you see on the screen, you have to move it up. 
**WAHEED QAMAR** *[19:40]*: Now. 
**Dmytro ZotkinDmytro Zotkin** *[19:43]*: Move it up. 
**Dmytro ZotkinDmytro Zotkin** *[19:43]*: It's blinking. Yeah, the mouse is blinking. I don't know why. 
**Dmytro ZotkinDmytro Zotkin** *[19:52]*: Maybe it's also your Internet connection. I don't know. Even this way. But you already went to a data map. 
**Dmytro Zotkin** *[20:07]*: You're already, like, too many. 
**Dmytro ZotkinDmytro Zotkin** *[20:11]*: Just close this data map. 
**WAHEED QAMAR** *[20:16]*: Just close it. 
**Dmytro ZotkinDmytro Zotkin** *[20:19]*: Yeah. And what you need to do. Okay, how do we do that? Because right now, you need to minimize it at the right. 
**WAHEED QAMAR** *[20:33]*: Okay, so how do we do that? 
**Dmytro ZotkinDmytro Zotkin** *[20:36]*: I'm having a meeting even on the smaller screen, so I cannot. Okay, so let me see if I can do. Wahid, can you release the control for mouse and keyboard? I will try to do something, because I kind of. 
**Dmytro ZotkinDmytro Zotkin** *[20:54]*: Yeah. Okay, now you can control. 
**WAHEED QAMAR** *[20:58]*: Yes, I think I can. Okay, that is moving really slow. Let me see. Here's. 
**Dmytro ZotkinDmytro Zotkin** *[21:15]*: No, it's not. It's not doing anything. 
**WAHEED QAMAR** *[21:18]*: I cannot. Oh, okay. And I need to minimize this one. How do I minimize this one? No. Okay. 
**Dmytro ZotkinDmytro Zotkin** *[21:46]*: No, the screen is broken completely. Okay, so this is. Where is. 
**WAHEED QAMAR** *[21:55]*: That screen is? Yeah. 
**Dmytro ZotkinDmytro Zotkin** *[22:05]*: That is something that moved down to the very bottom. So it takes the entire screen, and it needs to be just one secondary. 
**WAHEED QAMAR** *[22:23]*: And now let me try to. Now. Okay, so this is. No, this is, this is not working. So this one is here. 
**Dmytro ZotkinDmytro Zotkin** *[22:49]*: This one is here. There is a splitter, which you moved at the very bottom, and for some reason I cannot. 
**WAHEED QAMAR** *[22:57]*: Oh, here, I think I got it somehow. 
**Dmytro ZotkinDmytro Zotkin** *[23:03]*: Now go up a little higher. 
**IAIN MAGOWN** *[23:06]*: Hover your mouse above name and or property value. Maybe that'll. 
**Dmytro ZotkinDmytro Zotkin** *[23:12]*: No, I need a splitter at the bottom. And right now it's not moving because through three computers, it's just not sensitive. 
**WAHEED QAMAR** *[23:26]*: Enough to do anything. 
**Dmytro ZotkinDmytro Zotkin** *[23:29]*: I cannot get that splitter. Okay, so what do we do here. 
**WAHEED QAMAR** *[23:39]*: Let me do this and let me start it again. 
**Dmytro ZotkinDmytro Zotkin** *[23:48]*: Unfortunately, it remembers where splitter was, but let's hope that how to be. 
**bmagown@interweavebiz** *[23:56]*: Do we need more ram on this thing in this server? 
**Dmytro ZotkinDmytro Zotkin** *[24:00]*: I need to check that computer. It's not about Ram. 
**WAHEED QAMAR** *[24:03]*: No? 
**bmagown@interweavebiz** *[24:04]*: All right. 
**Dmytro ZotkinDmytro Zotkin** *[24:04]*: It's about cpu, and we need to check, maybe ask them if they can give us, like, one more spew. All right, because it's very slow. 
**bmagown@interweavebiz** *[24:15]*: Yeah, it's too slow. 
**Dmytro ZotkinDmytro Zotkin** *[24:17]*: And I'm trying to. 
**WAHEED QAMAR** *[24:19]*: No. What is it starting now. Nothing. It's not even starting. 
**Dmytro ZotkinDmytro Zotkin** *[24:33]*: Wahit. Can you get control back? 
**Dmytro ZotkinDmytro Zotkin** *[24:37]*: Okay. 
**Dmytro ZotkinDmytro Zotkin** *[24:39]*: Let me close this one back and try to start it again. Yeah, now it started. Very good. And let's see what will happen because hopefully it didn't save that splitter position. No, it did save it. Okay, so can you grab the splitter at the bottom or you cannot? 
**Dmytro ZotkinDmytro Zotkin** *[25:06]*: Okay, let's see if this works. 
**WAHEED QAMAR** *[25:11]*: No, it's not working. 
**Dmytro ZotkinDmytro Zotkin** *[25:16]*: The transaction window is gone. That's the problem. Okay, close it and let me fix it on the server. 
**Dmytro Zotkin** *[25:29]*: So give me a second, I will connect to the server and fix it here. 
**WAHEED QAMAR** *[25:33]*: Fix it. 
**Dmytro ZotkinDmytro Zotkin** *[25:34]*: Unfortunately that computer cannot be connected to. 
**Dmytro Zotkin** *[25:37]*: The meeting because it's special network. I don't allow any meetings to be conducted here. I'll fix it and then I will explain what I wanted to explain a little bit further. Just give me a second, I will jump on the server. 
**Dmytro ZotkinDmytro Zotkin** *[26:12]*: Bruce, one more thing that you need to ask. Trap. I don't know if it's possible or not, but can they actually give us. 
**Dmytro Zotkin** *[26:27]*: The second user for that server, second windows user and how much it will cost for us? 
**bmagown@interweavebiz** *[26:37]*: What will that do? 
**Dmytro ZotkinDmytro Zotkin** *[26:43]*: In this case I will be able to open the server and take a look while someone else is working with. 
**Dmytro Zotkin** *[26:52]*: Id for example, or with a portal. I will not be pushing away people. 
**WAHEED QAMAR** *[27:19]*: It will close. 
**Dmytro ZotkinDmytro Zotkin** *[27:23]*: For Wahid. Yes. 
**Dmytro Zotkin** *[27:29]*: Okay, so now let me connect to it. 
**WAHEED QAMAR** *[27:35]*: Let me fix the screen and after. 
**Dmytro ZotkinDmytro Zotkin** *[27:37]*: That it's opening for me. 
**WAHEED QAMAR** *[27:46]*: Okay. 
**Dmytro Zotkin** *[28:02]*: This window then. 
**WAHEED QAMAR** *[28:11]*: Just 1 second, let me fix everything here. 
**Dmytro ZotkinDmytro Zotkin** *[28:19]*: No, it's not that bad. Like on my computer it opens pretty. 
**Dmytro Zotkin** *[28:22]*: Fast and works normally. 
**Dmytro ZotkinDmytro Zotkin** *[28:30]*: I just need to work with resolution. 
**WAHEED QAMAR** *[28:32]*: Of this screen a little bit. 
**bmagown@interweavebiz** *[28:37]*: Could be a regional thing or something along those lines or maybe just be refreshed every now and then. Internet goes in and out. 
**Dmytro Zotkin** *[28:49]*: Okay, so here and then I need to somehow it's going to affect the. 
**Dmytro ZotkinDmytro Zotkin** *[28:55]*: UI of it though. 
**Dmytro Zotkin** *[28:59]*: Just 1 second please. 
**bmagown@interweavebiz** *[29:01]*: Waheed needs to put more firewood in the fire and the stove. That's what powers the Internet there. They're moving along pretty well. 
**Dmytro Zotkin** *[29:15]*: Okay, I know what to do. 
**WAHEED QAMAR** *[29:36]*: You on? No, that's not. 
**Dmytro Zotkin** *[29:42]*: We can see here. I don't understand why it's not. 
**WAHEED QAMAR** *[29:53]*: Need to recall how to put that window under here's. 
**Dmytro ZotkinDmytro Zotkin** *[30:04]*: Is it possible for you to share your screen? 
**WAHEED QAMAR** *[30:06]*: Just. 
**Dmytro Zotkin** *[30:07]*: No, unfortunately not. Because that computer is not connected to. I cannot do this with that computer. So just give me 1 second, I'll change the view. 
**bmagown@interweavebiz** *[30:38]*: Copy that. 
**Dmytro Zotkin** *[30:41]*: Configuration resource. Okay, so this one is done. 
**WAHEED QAMAR** *[30:52]*: Now I need to somehow, what is this thing that I need to. 
**Dmytro Zotkin** *[31:21]*: Where is transaction view? Transaction detail view here. 
**WAHEED QAMAR** *[31:29]*: How do I. 
**Dmytro Zotkin** *[31:33]*: Okay, hold on for a second. 
**WAHEED QAMAR** *[31:35]*: It needs to. 
**Dmytro Zotkin** *[31:40]*: Okay, so now it's close to what I wanted to do. Now I just need to put it down somehow and that will know. 
**WAHEED QAMAR** *[31:58]*: How do I, so slow. So I cannot even. 
**Dmytro ZotkinDmytro Zotkin** *[32:24]*: Okay fine. 
**Dmytro Zotkin** *[32:26]*: I file exit and resolution. Let me look at the resolution of. 
**WAHEED QAMAR** *[32:39]*: This screen because that needs to be a little bit fixed. Ground color themes, no more. 
**Dmytro Zotkin** *[33:04]*: Here where. 
**WAHEED QAMAR** *[33:05]*: The display, okay. 
**Dmytro Zotkin** *[33:20]*: Wants display settings here. 
**WAHEED QAMAR** *[33:23]*: Resolution. 
**Dmytro Zotkin** *[33:28]*: It doesn't allow me to change. 
**Dmytro ZotkinDmytro Zotkin** *[33:32]*: Oh, RTP doesn't allow to change resolutions. 
**Dmytro Zotkin** *[33:41]*: Only from this session. Okay, so I'll double check this vahid. 
**Dmytro ZotkinDmytro Zotkin** *[33:48]*: You can share the server again. 
**Dmytro ZotkinDmytro Zotkin** *[33:52]*: Okay, yeah, let me open. 
**Dmytro Zotkin** *[33:54]*: Not you cannot change screen resolution through RDP session. 
**WAHEED QAMAR** *[33:58]*: Unfortunately. 
**Dmytro ZotkinDmytro Zotkin** *[34:02]*: I will reset these parameters from the server. Okay, so now I can start id actually. So when you go to the server you will see it. When you open the screen you should see actual id opened. 
**Dmytro ZotkinDmytro Zotkin** *[34:35]*: Yeah, I can see. 
**Dmytro Zotkin** *[34:37]*: Okay, so now you can actually. 
**Dmytro ZotkinDmytro Zotkin** *[34:42]*: Show. 
**WAHEED QAMAR** *[34:42]*: It to all of us. 
**Dmytro ZotkinDmytro Zotkin** *[34:47]*: Okay. 
**WAHEED QAMAR** *[34:47]*: Secret. 
**Dmytro ZotkinDmytro Zotkin** *[35:04]*: So let's open again that particular project. Accept notorized net on the left side and open integration flow, transaction flows and yeah, this one doesn't matter which one. Okay, so this picture is not 100% how this flow can look like. If you scroll down, I will just show you. Then we'll return back to this flow. Scroll down, not here on the list here. Just scroll down and open. SF two QB based project. Expand SF to QB based scroll. Yeah, expand and go to integration flow. Transaction flows and open. For example, scroll down again. Now see SFAC to QB cast in. No, SFAC or PP to could be cast in. It's a little bit five now alphabetical. 
**WAHEED QAMAR** *[36:31]*: No. 
**Dmytro ZotkinDmytro Zotkin** *[36:33]*: You need to expand that or just move it to the right a little bit. Yeah, so see SF ACCT Opp to QB cast inf it's a little bit higher. No, you don't need to scroll, just move your mouse, actually your mouse to the left of mouse. That's the transaction. Not east, not two doubt invite. You will see that. Actually the picture is much more complicated here. So this transaction flow, it has like branches, it's like a tree. So you can see that we are looking at a simple transaction. It's more complicated transaction with conditional elements inside transaction flows. So you can create branches. And that mechanism is pretty simple. So depending on the result of the previous transaction, you go to different transactions, you can move to the left again, it's a conditional thing. 
**Dmytro ZotkinDmytro Zotkin** *[37:54]*: Like next conditional transaction, but we will return back to the flow which is authorized. Net, which is unconditional. That's the one I was just showing you how that's more or less a serious process, how it looks like it's kind of a bit more complicated, but let's talk about the simpler one. So scroll up on the left side and let's go back to the transaction. 
**Dmytro Zotkin** *[38:20]*: That we just looked at. 
**WAHEED QAMAR** *[38:24]*: Double click at it. 
**Dmytro ZotkinDmytro Zotkin** *[38:26]*: Okay, now each element of this picture on the top is a transaction. So if you double click at first transaction, you will see the transaction details at the bottom. See there is a name and it's adapter. So if you open this list of adapters, you will see all types of adapters that we have here, like in documentation. Each type of adapter as explained, it has many different types of data that it can process. So these are the adapters. They are determining how you will be connecting to the integration point. Now the next element of the flow, like skip final transformer. It's a special case when you need to make a transformation after the transaction finished. Like it's kind of outside of transaction based on the result of the transaction. 
**Dmytro ZotkinDmytro Zotkin** *[39:38]*: It's used rarely, but it is used especially for the flows which are queries which are going with the rest, like pseudo rest API that we have. Look at the element which is called data map. Data map, see data maps. And each transaction can contain multiple data maps. Data map is actually one step of the data transformation and connectivity to the elements. So it consists of connection and data transformation. You will see the structure I will show you. So double click at the login. Okay, so it shows the transaction name of transaction, connection, see like that, how it's connecting to data point. If you click at the edit of the connection on the right side. 
**WAHEED QAMAR** *[40:48]*: Yeah, no, edit, see edit button. 
**Dmytro ZotkinDmytro Zotkin** *[40:52]*: Just click on it. See at the left bottom corner it's parameter of connection. It brings SF URL. SF URL is determined as a parameter for Salesforce URL username and password. That's like connection parameters that are sitting here. This username and passwords are taken out of the configuration. So it's just a placeholder. But what it does actually it uses the type is purely it's a connection with the soap adapter. So it's Htps connection inside of the text area. It can be a command or it could be a transformer. So command is used when the type access type is procedure. So here is just a soap message. It's the simplest case of connectivity. If you scroll it down you will see that this is like a soap message to log in. 
**Dmytro ZotkinDmytro Zotkin** *[42:01]*: You can continue scrolling to login to Salesforce and see it has those parameters in percentage signs those parameters are taken from the connection. If you look at the actually not through the connection, it's a little bit more complicated, but it doesn't really matter. It has more other parameters which we'll talk later. Important thing that if it was not a procedure and we will take a look at it, see can you go to the tab SF transaction? Like on top left there is a tab of the flow itself, no higher. And there is a tab. This one, yes. Click here and open for example like the third transaction and double click at the data map. So this is hierarchical adapter. 
**Dmytro Zotkin** *[43:05]*: It's a little bit different. 
**Dmytro ZotkinDmytro Zotkin** *[43:07]*: See it says access type dynamic. And here is pre transformer that what actually controls getting Salesforce account out information about Salesforce record. If you want to look at this transformer like before looking at that transformer, see it says parameter zero parameters at the bottom. So if you double click at that parameter, see at the bottom, it's URL of a SAP. So that URL actually if you go to mapping value, see mapping type is xpass. That's when you, if you like a little bit familiar with xslt xpath. So it's xpath. And now the mapping value is actually xpath expression. So if you go to the next position mapping value and scroll it to the right, just click on it and scroll to the right. More to the right, more to the right. See, it says data map equals login. 
**Dmytro ZotkinDmytro Zotkin** *[44:16]*: It means that it takes Salesforce URL as a result of the transaction login. So when you do login to Salesforce, it returns to you URL. So this particular parameter is specifying sF URL which will be used now in the transformer itself. So what it does, that's how we are moving parameters between different transactions, not just the data, but like parameters which are global parameters. For the, it's not part of the data, it's kind of a metadata parameter. You will understand later, it's just a review. But when we will start talking about each element in details, then I will point you out to the documentation. It's very well described in there. So now just click at the pretransformer. It's a pretransformer in the top window. In the top window. In the top window pretransformer, see, get a step account. 
**Dmytro ZotkinDmytro Zotkin** *[45:24]*: Click at the button on the right side. No, not the drop down the button near drop down on the right side. 
**WAHEED QAMAR** *[45:31]*: Yeah, here, just click on it. 
**Dmytro ZotkinDmytro Zotkin** *[45:35]*: So this is the transformer itself. Generally speaking, it has a template editor. If you kind of. For some elements we can use templates. See on the top there is a template editor. If you click on the template editor it will show you the template. But there is no template for this particular transaction that allows you graphically map the data elements. Like if you go back to the transaction, not to the template editor, but here. So this is a text view of the transaction. You can see that output type is soap. And if you scroll down a little bit, continue scrolling down. It's xsl key code, by the way, if you look at the session id, you will see that it refers to transaction SF login which we looked first. See session id a little bit higher. A little bit more higher. 
**WAHEED QAMAR** *[46:43]*: With this. 
**Dmytro ZotkinDmytro Zotkin** *[46:44]*: Yeah. Here, session id in the middle, can you see session id variable name? Yeah. So that's how it's getting data from the SF login transaction. So it refers to the previous transaction, that expression which is XSLT Xpass expression, it described with the format of our system. And that format is IWP format. We will talk about it, IW protocol IWP format, which we'll talk about it later, but that the protocol, which actually consists of columns, rows, like it parses the data and makes it convenient to process. It's parsing data coming from JSON, it's parsing data coming from sOap, it's parsing data coming from SQL queries or results of some other API, local or global. It always represents it as granular as possible in the internal form. 
**Dmytro ZotkinDmytro Zotkin** *[47:52]*: So now if you go scroll down, you will see that actually this particular transformer is basically querying the, it's a sop API for Salesforce and it's querying the count object. So that's a query which is sitting in here. But see like the query is by itself, it's XSLT expression and it contains the elements at the bottom. See for example value of select Q two or like current days. And all these variables are determined on top of that query. So they are replaced when transformation is happening. So this particular transformer creates the query which is sent to Salesforce and it actually reads the information from Salesforce record. Like that information will contain the fields that are used to pay the credit card. So this is how it looks like. 
**Dmytro ZotkinDmytro Zotkin** *[49:01]*: Now if you look at the right top corner, see there is an icon like a note. Down a little bit, down a little bit, yeah, that one that is system editor. So don't start it. Now what I would like you to do, actually you guys can talk to each other to do some small research and install on this test server any free XML Xslt editor of your choice. Because there are free editors that allow you to look at the XML and XSLT documents. Important thing, make sure that they allow to debug XSLT documents to run debugger and to see what will be the results of the transformation. So Xpass xsLt xML editor, after that I will link it to this particular button. 
**Dmytro ZotkinDmytro Zotkin** *[50:04]*: So after you click at that button, this transformer will go to the editor and you will be able to work with the XsL key editor over there. 
**bmagown@interweavebiz** *[50:13]*: Demetri, I got a question for you. So I'm looking at here, I see all the fields and I believe this is an account call here, not necessarily the account where's the object that we're working with here? 
**Dmytro ZotkinDmytro Zotkin** *[50:27]*: It says from account where it says from account. So yeah, this one takes it from account. Yes, because this flow is for account in particular. Got it. So this is from, again, like that's purely soap API. Like all these soap calls are available in Salesforce documentation and it's very simple to build. We just copy pasting examples from there and replacing their data to XsLT expressions. It's all done to make the process of development efficient and fast. So this is how right now went kind of the stairway down from the project to flow to transaction to data map. And inside data map went to transformer. These are the levels of elements of the system that are used to create those flows. Like if you go back to see on the top there is like an icon with a clock, right. 
**Dmytro ZotkinDmytro Zotkin** *[51:45]*: And then icon near icon with a tree. So click at the icon with the tree. This one, yes. And switch back to the flow itself. So you can see that this makes retrieve SF account. Net transaction will be payauthorized. Net next transaction will be create transaction object and then update account object. So it's kind of very simple flow which actually does simple procedure of paying credit card to authorize. 
**WAHEED QAMAR** *[52:22]*: Net. 
**Dmytro ZotkinDmytro Zotkin** *[52:25]*: If you clock. That's an interesting thing. So click please on the next transaction to the right, double click and then click at the data map. Double click at the data map and open the transformer here and scroll down. Scroll down. You can see that actually this one is building name value pairs. So it's not soap, it's just name value pairs which will be sent to authorize. Net. It's there like classic API. Now they are trying to switch to rest API, but this is there. So x delimiter data, this variable like login, this one API key, transaction key, this one first name, last name, address, all these parameters. This is how the message to authorize. Net looks like. 
**Dmytro ZotkinDmytro Zotkin** *[53:29]*: So what it does, it takes information from the account transaction after that query and puts it into this particular name value pairs document and sends name value pairs document to authorize. 
**WAHEED QAMAR** *[53:45]*: Net. 
**Dmytro ZotkinDmytro Zotkin** *[53:46]*: So this is like simple example how data are transformed. Any questions regarding what I just shown to you? I understand that there are like a lot of information and a lot of things are kind of not clear or not understandable yet, but at least about the process and the structure. 
**bmagown@interweavebiz** *[54:14]*: Additionally, I'll send you the we have a document on this. 
**Dmytro ZotkinDmytro Zotkin** *[54:17]*: No, Bruce, I said you don't have a latest one. 
**bmagown@interweavebiz** *[54:20]*: You don't want to show the document. 
**Dmytro ZotkinDmytro Zotkin** *[54:25]*: I'll send the document. I'll send to everyone the newest documents because there are documents which are not fully published because it's internal tool at the moment. Just from the history perspective, I can update you a little bit. At the very beginning when we had that big customer, you remember what was it like? Forgot the name of the customer? 
**bmagown@interweavebiz** *[54:54]*: Yeah, it was a while ago, but. 
**Dmytro ZotkinDmytro Zotkin** *[54:57]*: The big one which actually paid good money. So we actually wanted to sell this id. 
**bmagown@interweavebiz** *[55:03]*: Correct. 
**Dmytro ZotkinDmytro Zotkin** *[55:04]*: That was a period when were trying to sell it. Unfortunately at that point I don't know what went wrong because considering that it's actually working with all new API and all new elements and doesn't require significant updates, the schema and the technology behind it was ahead of the time. Maybe it was too much ahead of the time and that's why we couldn't sell it, because basically the full power of this tool is available to be used just now. Because before there were not that many different API types and remote access points, most of the systems even didn't have remote API. So right now, every system, every financial system, CRM system, actually every system has a remote API and that tool allows you to connect to them. Pretty simple way. 
**Dmytro ZotkinDmytro Zotkin** *[56:23]*: Maybe we will return to it a little bit later, especially after we switch to the contemporary version of Java. That could be also fun to try. 
**bmagown@interweavebiz** *[56:36]*: A question here. So as they're reviewing all this, we're not making changes to this, correct. 
**Dmytro ZotkinDmytro Zotkin** *[56:44]*: You can do whatever you want because that's a copy. Of course they can break everything and it will stop working. 
**bmagown@interweavebiz** *[56:56]*: What's the best way? 
**Dmytro ZotkinDmytro Zotkin** *[56:58]*: The best way is after this introduction they can start reviewing documentation and that should be the process where they go through, they have to create guys, that's between you. Three of you create like a schedule when you are accessing the tool and working with it, because that's only one user right now for the server. 
**bmagown@interweavebiz** *[57:22]*: Oh, I get it. 
**Dmytro ZotkinDmytro Zotkin** *[57:25]*: They cannot do it together. And after they do that, when they start looking at documentation, they can correspondently go internally and check what is going on and how it looks in real life because documentation is documentation, but you can always find similar examples in there. We need to have scheduled calls like this so far, say every three days. Bruce, you have to figure it out like maybe 30 minutes calls when they will be able to ask questions and by the type and the content of questions, I will see the progress. So again, the schedule. Remember that first degree between you, between all of you, who will be installing, whose task will be to install XML accesslt editor. Because without that editor you won't be able to do any test processes. 
**Dmytro ZotkinDmytro Zotkin** *[58:46]*: There are test projects, I will show them to you during the next call where you can actually create something and you can do simple thing like for example send an email using that tool, or build something that reads information from some simple database, like, I don't know, access database. Something that we will discuss like what you can do or maybe just to do something with the creator, or at least with the creator, some simple project which either reads or writes information to create, you say, from one object to another. Very simple, very simple flow. But of course before you have to go through documentation. So I will send right after that meeting, I will check the latest documents and I will send, I believe there are two documents. 
**Dmytro ZotkinDmytro Zotkin** *[59:48]*: One of them is introduction like quick start, and another one is full reference of all adapters, all parameters of the flows. 
**Dmytro Zotkin** *[01:00:00]*: You will be able to see them, you will be able to understand what. 
**Dmytro ZotkinDmytro Zotkin** *[01:00:03]*: I mean, at least to see what they are. If something is not clear, you will let me know and I will definitely answer to all questions. If you feel like that, it will be more convenient. You can send to me an email with some questions prior to the call. So I'll prepare the more extended answers. Again, you can organize it the way that actually you will give a combined list or you can send emails personally. Doesn't matter. For me, that's like your choice. That's it. About the process and any final questions? 
**bmagown@interweavebiz** *[01:01:04]*: No questions from my side quite yet. I think I'll probably have more questions once I start experimenting and kind of going through the process. So until then I'll just kind of hold off on broad questions. 
**Dmytro ZotkinDmytro Zotkin** *[01:01:20]*: Okay, very good. So in this case, what time is it? We are 1 minute behind. That's not good. Okay, anyways, dad, you're currently muted. Who's currently muted? Unmute. 
**bmagown@interweavebiz** *[01:01:44]*: Okay, so just one question here. We've been working on getting the documentation at page. 
**Dmytro ZotkinDmytro Zotkin** *[01:01:53]*: Yeah, I see this. What is required? So first of all, Bruce, we discussed already. Unfortunately today it's a little bit too late, but we need a special call when we will go through all these elements, because there are a lot of errors. And first of all, the SF account contact to be customers should not exist. All these headers should be removed. They should not exist. Now, for example, if you look at what documentation says, try to write at least something that more or less say, for example, if you scroll down a. 
**WAHEED QAMAR** *[01:02:38]*: Little bit, where is it? 
**Dmytro ZotkinDmytro Zotkin** *[01:02:47]*: It's not here. 
**WAHEED QAMAR** *[01:02:56]*: Okay, hold on. 
**Dmytro ZotkinDmytro Zotkin** *[01:02:58]*: Okay. 
**WAHEED QAMAR** *[01:03:04]*: What is this? 
**Dmytro ZotkinDmytro Zotkin** *[01:03:08]*: Well, this correlates the column that you have. That you have. Like the third column. I don't know if it makes sense at all. I think it makes it a little bit more convoluted. So the second column, what is the second column? Is it default? 
**bmagown@interweavebiz** *[01:03:34]*: Yeah. Yes or no? Your shipping address? Yes. No, things like that. 
**Dmytro ZotkinDmytro Zotkin** *[01:03:39]*: Oh, no. 
**bmagown@interweavebiz** *[01:03:40]*: Shipping address is billing. 
**Dmytro ZotkinDmytro Zotkin** *[01:03:47]*: This structure has to be changed because it's not. 
**WAHEED QAMAR** *[01:03:50]*: All right. 
**Dmytro ZotkinDmytro Zotkin** *[01:03:57]*: I will try to find a document which has a proper structure. I will send you some because it has a lot of useful information, but the way it's represented, it's not intuitive enough. It is clear after you know what it is. 
**bmagown@interweavebiz** *[01:04:16]*: The goal here is on every configuration. 
**Dmytro ZotkinDmytro Zotkin** *[01:04:20]*: Page we have, every section has to have this document. But this document has to be intuitive so people click and they understand what it is. 
**IAIN MAGOWN** *[01:04:30]*: Can you send me a copy of that too? 
**bmagown@interweavebiz** *[01:04:34]*: Yeah, it's URL. 
**Dmytro ZotkinDmytro Zotkin** *[01:04:37]*: Yeah, that is available everywhere. 
**IAIN MAGOWN** *[01:04:40]*: Demetrius, can you send me the example that you're talking about too? 
**Dmytro ZotkinDmytro Zotkin** *[01:04:45]*: Oh, I will send it to, like, are all three of you or four of you working on the documentation? So should I send it to all four of you? 
**bmagown@interweavebiz** *[01:04:59]*: It's been waheed and I working on doing the setup. So we've been looking for, this is a WordPress document. 
**Dmytro ZotkinDmytro Zotkin** *[01:05:10]*: I just need to find the moment to find the document that actually I'm talking about. 
**bmagown@interweavebiz** *[01:05:17]*: We've been working on setup, looking for a way that you can edit in, what is it, word or excel? I can't remember. 
**Dmytro ZotkinDmytro Zotkin** *[01:05:24]*: It's a Google spreadsheet. 
**bmagown@interweavebiz** *[01:05:26]*: And then we converted this to, okay, funny. We went to Google spreadsheet, which then produces this, which is WordPress. 
**Dmytro ZotkinDmytro Zotkin** *[01:05:37]*: This is a PDF. So we convert Google spreadsheet to a word document and then a word to PDF and PDF to WordPress. 
**Dmytro ZotkinDmytro Zotkin** *[01:05:44]*: Okay. So the point was, that's a good technology way. That's correct. 
**bmagown@interweavebiz** *[01:05:53]*: We can update this in Google at any time. That was kind of the point. And then it just goes right over into WordPress in this. 
**Dmytro ZotkinDmytro Zotkin** *[01:06:02]*: Me. Just one step at a time. 
**bmagown@interweavebiz** *[01:06:05]*: Yeah, I just wanted to show. 
**Dmytro ZotkinDmytro Zotkin** *[01:06:07]*: So let me first send to everyone id documents. And Bruce, you will create a schedule for the next calls. Which days is better to use? I don't want Monday, so basically it should be Tuesday and Friday. That's what I think we need to, because we don't have any allocated things. I don't want to use Monday because they run too early. So Wednesday and Thursday, it's a MBox and national stuffing. So Tuesday and Friday for now. 
**Dmytro Zotkin** *[01:06:50]*: Like two days, right? 
**Dmytro ZotkinDmytro Zotkin** *[01:06:54]*: For 30 minutes. Minutes. All right. Okay. Thank you very much, everyone. 
**Dmytro ZotkinDmytro Zotkin** *[01:07:01]*: Thank you. 
**Dmytro ZotkinDmytro Zotkin** *[01:07:02]*: Don't be overwhelmed. Eventually you will get all this. Everything is fine. I'll show you all details, step by step. Don't worry. 
**bmagown@interweavebiz** *[01:07:15]*: We're getting all you guys laptops, so you can bring this anywhere you go, sporting event, on a date or anything. You just bring it up there and start working on. It'd be great. 
**IAIN MAGOWN** *[01:07:26]*: Now. 
**Dmytro ZotkinDmytro Zotkin** *[01:07:30]*: Just now, Bruce, over. 
**bmagown@interweavebiz** *[01:07:35]*: No dissension. No, we don't do it. 
**Dmytro ZotkinDmytro Zotkin** *[01:07:37]*: Goodbye. Okay, bye now. 
**Dmytro ZotkinDmytro Zotkin** *[01:07:42]*: Bye. 
**WAHEED QAMAR** *[01:07:42]*: Thank you. Okay. 
